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
import de.openknowledge.workshop.serverless.oms.model.OrderStatus;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepository;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepositoryFactory;
import de.openknowledge.workshop.serverless.oms.repository.OrderRepositoryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Service to create an order
 */
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    private static OrderRepositoryType ORDER_REPOSITORY_TYPE = OrderRepositoryType.DYNAMODB_ENHANCED;
    private static OrderRepository repository = OrderRepositoryFactory.getOrderRepository(ORDER_REPOSITORY_TYPE);

    // private static OrderRepositoryType ORDER_REPOSITORY_TYPE = OrderRepositoryType.UNKNOWN;
    // private static OrderRepositoryType ORDER_REPOSITORY_TYPE = OrderRepositoryType.DYNAMODB_LOCAL;
    // private static OrderRepositoryType ORDER_REPOSITORY_TYPE = OrderRepositoryType.DYNAMODB;


    public static void setOrderRepositoryType(OrderRepositoryType type) {
        // reinitialize repository
        if (!ORDER_REPOSITORY_TYPE.equals(type)) {
            logger.info(String.format("Set repository type from %s to %s",ORDER_REPOSITORY_TYPE, type ));
            ORDER_REPOSITORY_TYPE = type;
            repository = OrderRepositoryFactory.getOrderRepository(type);
        }
    }

    /**
     * Reads order with the help of
     *
     * @param orderId unique id of order
     */
    public static Order readOrder(String orderId) {

        logger.info("READ ORDER - START");
        logger.info(String.format("READ ORDER - repository type %s (read)", ORDER_REPOSITORY_TYPE));

        Order order = repository.readOrder(orderId);

        logger.info("READ ORDER - END");

        return order;
    }


    /**
     * Generates order with the help of
     *
     * @param userId user to create order for
     * @param drink  drink ordered
     * @return order id created for the new order
     */
    public static Order createOrder(String userId, String drink) {

        logger.info("CREATE ORDER - START");
        logger.info(String.format("CREATE ORDER - repository type %s (store)", ORDER_REPOSITORY_TYPE));

        // step 1: create new order with
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderNo(OrderCounterService.next());
        order.setDrink(drink);
        order.setUserId(userId);
        order.setOrderStatus(OrderStatus.CONFIRMED.getStatus());

        // step 2: store order in Data Storage (e.g. Dynamo DB)
        repository.storeOrder(order);

        logger.info("CREATE ORDER - END");

        return order;
    }

    /**
     *
     * @param order
     * @return
     */
    public static Order updateOrder(Order order) {

        logger.info("UPDATE ORDER - START");
        logger.info(String.format("UPDATE ORDER - repository type %s (update)", ORDER_REPOSITORY_TYPE));


        // TODO
        // - call repository to update existing order
        // - return updated order

        // store order in Data Storage (e.g. Dynamo DB)
        Order updatedOrder = repository.updateOrder(order);

        logger.info("UPDATE ORDER - END");

        return updatedOrder;
    }


    /**
     * Cancels order with the help of
     *
     * @param orderId unique id of order
     */
    public static Order cancelOrder(String orderId) {

        logger.info("CANCEL ORDER - START");
        logger.info(String.format("CANCEL ORDER - repository type %s (read & update)", ORDER_REPOSITORY_TYPE));

        Order order = repository.cancelOrder(orderId);

        logger.info("CANCEL ORDER - END");

        return order;
    }

    public static void deleteOrder(String orderId) {

        logger.info("DELETE ORDER - START");
        logger.info(String.format("DELETE ORDER - repository type %s (remove)", ORDER_REPOSITORY_TYPE));

        repository.deleteOrder(orderId);

        logger.info("DELETE ORDER - END");
    }


    public static void main(String[] args) {
        setOrderRepositoryType(OrderRepositoryType.DYNAMODB_LOCAL);
    }

}