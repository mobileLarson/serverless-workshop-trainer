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
import de.openknowledge.workshop.serverless.oms.model.OrderStatus;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepository;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepositoryFactory;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepositoryType;
import de.openknowledge.workshop.serverless.util.LambdaEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service to create an order
 */
public class CreateOrderService {

    private static final Logger logger = LoggerFactory.getLogger(CreateOrderService.class);

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
    private static final String EVENT_DETAIL_TYPE_ORDER_CREATED = "OrderManagementService.OrderCreated";


    /**
     * Generates order with the help of
     *
     * @param userId user to create order for
     * @param drink  drink ordered
     * @return order id created for the new order
     */
    public static Order createOrder(String userId, String drink) {

        logger.info("CREATE ORDER - START");
        logger.info("CREATE ORDER - 1. repository: store");

        // step 1: create new order
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderNo(OrderIdService.next());
        order.setDrink(drink);
        order.setUserId(userId);
        order.setOrderStatus(OrderStatus.CONFIRMED.getStatus());

        // step : stor order in Data Storage (e.g. Dynamo DB)
        repository.storeOrder(order);

        logger.info(String.format("CREATE ORDER - 2. event handler: put(%s)", EVENT_DETAIL_TYPE_ORDER_CREATED));

        // step 3: create domain event and put it to app event handler system
        String eventId = eventHandler.putEvent(
                EVENT_SOURCE,
                EVENT_DETAIL_TYPE_ORDER_CREATED,
                generateDetailMap(order));

        logger.info(String.format("CREATE ORDER - END (eventId: %s)", eventId));

        return order;
    }

    /**
     *
     * @param order
     * @return
     */
    private static Map generateDetailMap(Order order) {
        Map detailMap = new HashMap<String, String>();
        detailMap.put("userId", order.getUserId());
        detailMap.put("orderId", order.getOrderId());
        detailMap.put("drink", order.getDrink());
        return detailMap;
    }
}