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
package de.openknowledge.workshop.serverless.service;

import de.openknowledge.workshop.serverless.model.Order;
import de.openknowledge.workshop.serverless.model.OrderStatus;
import de.openknowledge.workshop.serverless.repository.DynamoDbOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Service to create an order
 */
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private static final DynamoDbOrderRepository repository = new DynamoDbOrderRepository();

    /**
     * Generates order with the help of
     *
     * @param userId user to create order for
     * @param drink  drink ordered
     * @return order id created for the new order
     */
    public static Order createOrder(String userId, String drink) {

        // create new order with given values
        Order order = new Order();

        order.setDrink(drink);
        order.setUserId(userId);

        // set missing values for order to create
        // - orderId : unique order id via private method
        // - orderNo : next available order no via via private method
        // - orderStatus: order confirmed state via OrderStatus
        order.setOrderId(generateUniqueOrderId());
        order.setOrderNo(nextAvailableOrderNo());
        order.setOrderStatus(OrderStatus.CONFIRMED.getStatus());

        // - store order via repository
        repository.storeOrder(order);

        logger.info(String.format("Order created: %s", order));

        return order;
    }

    //----- PRIVATE METHODS

    public static final String generateUniqueOrderId() {
        return UUID.randomUUID().toString();
    }

    public static final Long nextAvailableOrderNo() {
        return ThreadLocalRandom.current().nextLong(100);
    }
}