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


import de.openknowledge.workshop.serverless.oms.model.Order;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepository;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepositoryFactory;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepositoryFilter;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepositoryType;
import de.openknowledge.workshop.serverless.util.LambdaEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Service to create an order
 */
public class ReadOrderService {

    private static final Logger logger = LoggerFactory.getLogger(ReadOrderService.class);

    private static final OrderRepository repository;
    private static final OrderRepositoryType ORDER_REPOSITORY_TYPE;
    private static final Boolean MOCK_DATABASE;

    static {
        MOCK_DATABASE = LambdaEnvironment.getEnvVarAsBoolean("MOCK_DATABASE", Boolean.FALSE);
        ORDER_REPOSITORY_TYPE = MOCK_DATABASE? OrderRepositoryType.MOCK : OrderRepositoryType.DYNAMODB_ENHANCED;
        repository = OrderRepositoryFactory.getOrderRepository(ORDER_REPOSITORY_TYPE);
    }

    /**
     * Reads order with the help of
     *
     * @param orderId unique id of order
     */
    public static Order readOrder(String orderId) {

        logger.info("READ ORDER - START");
        logger.info(String.format("READ ORDER - via %s", ORDER_REPOSITORY_TYPE));

        Order order = repository.readOrder(orderId);

        logger.info("READ ORDER - END");

        return order;
    }

    public static List<Order> readOrders(OrderRepositoryFilter filter, Map<String,String> filterAttributes) {

        logger.info("READ ORDERS - START");
        logger.info(String.format("READ ORDER - via %s with filter of type %s", ORDER_REPOSITORY_TYPE, filter));

        List<Order> orders = repository.readOrders(filter, filterAttributes);

        logger.info("READ ORDERS - END");

        return orders;

    }

}