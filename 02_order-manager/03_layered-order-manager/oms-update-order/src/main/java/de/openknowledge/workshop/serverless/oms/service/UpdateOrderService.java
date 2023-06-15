/*
 * Copyright (C) open knowledge GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package de.openknowledge.workshop.serverless.oms.service;

import de.openknowledge.workshop.serverless.oms.eventhandler.EventHandler;
import de.openknowledge.workshop.serverless.oms.eventhandler.EventHandlerFactory;
import de.openknowledge.workshop.serverless.oms.eventhandler.EventHandlerType;
import de.openknowledge.workshop.serverless.oms.model.Order;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepository;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepositoryFactory;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepositoryType;
import de.openknowledge.workshop.serverless.util.LambdaEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Service to create an order
 */
public class UpdateOrderService {

    private static final Logger logger = LoggerFactory.getLogger(UpdateOrderService.class);

    private static final OrderRepository repository;
    private static final OrderRepositoryType ORDER_REPOSITORY_TYPE;
    private static final Boolean MOCK_DATABASE;

    private static final EventHandler eventHandler;
    private static final EventHandlerType EVENT_HANDLER_TYPE;
    private static final Boolean MOCK_EVENT_HANDLER;

    static {

        // get repository of preferred type
        MOCK_DATABASE = LambdaEnvironment.getEnvVarAsBoolean("MOCK_DATABASE", Boolean.FALSE);
        ORDER_REPOSITORY_TYPE = MOCK_DATABASE? OrderRepositoryType.MOCK : OrderRepositoryType.DYNAMODB_ENHANCED;
        repository = OrderRepositoryFactory.getOrderRepository(ORDER_REPOSITORY_TYPE);

        // get event handler of preferred type
        MOCK_EVENT_HANDLER = LambdaEnvironment.getEnvVarAsBoolean("MOCK_EVENT_HANDLER", Boolean.FALSE);
        EVENT_HANDLER_TYPE = MOCK_EVENT_HANDLER? EventHandlerType.MOCK : EventHandlerType.EVENT_BRIDGE;
        eventHandler = EventHandlerFactory.getEventHandler(EVENT_HANDLER_TYPE);
    }

    private static final String EVENT_SOURCE = "ok.serverlessworkshop";
    private static final String EVENT_DETAIL_TYPE_ORDER_UPDATED = "OrderManagementService.OrderUpdated";

    /**
     *
     * @param order
     * @return
     */
    public static Order updateOrder(Order order) {

        logger.info("UPDATE ORDER - START");
        logger.info("UPDATE ORDER - 1. repository: update");

        Order updatedOrder = repository.updateOrder(order);

        logger.info(String.format("UPDATE ORDER - 2. event handler: put(%s)", EVENT_DETAIL_TYPE_ORDER_UPDATED));

        String eventId = eventHandler.putEvent(
                EVENT_SOURCE,
                EVENT_DETAIL_TYPE_ORDER_UPDATED,
                generateDetailMapForOrderUpdated(updatedOrder));

        logger.info(String.format("UPDATE ORDER - END (eventId: %s)", eventId));

        return updatedOrder;
    }

    private static Map generateDetailMapForOrderUpdated(Order order) {
        Map detailMap = new HashMap<String, String>();

        detailMap.put("orderId", order.getOrderId());
        detailMap.put("orderNo", order.getOrderNo().toString());
        detailMap.put("orderStatus", order.getOrderStatus());
        detailMap.put("userId", order.getUserId() );
        detailMap.put("drink", order.getDrink());

        if (order.getBaristaId() != null && !order.getBaristaId().isEmpty()) {
            detailMap.put("baristaId", order.getBaristaId());
        }


        return detailMap;
    }
}