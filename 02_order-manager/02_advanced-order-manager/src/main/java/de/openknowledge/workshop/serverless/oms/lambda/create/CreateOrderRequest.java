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
package de.openknowledge.workshop.serverless.oms.lambda.create;

import de.openknowledge.workshop.serverless.oms.lambda.OrderAction;
import de.openknowledge.workshop.serverless.oms.lambda.OrderRequest;
import de.openknowledge.workshop.serverless.oms.model.Order;

import java.util.Optional;

/**
 * Wrapper object for order information
 */
public class CreateOrderRequest {

    private String userId;
    private String drink;

    public CreateOrderRequest(OrderRequest orderRequest) {
        if (isValidCreateOrderRequest(orderRequest)) {
            this.userId = orderRequest.getOrder().getUserId();
            this.drink = orderRequest.getOrder().getDrink();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public boolean isValid() {
        return  userId != null && drink != null;
    }

    public static boolean isValidCreateOrderRequest(OrderRequest request) {

        // check for action
        Optional<OrderAction> action = OrderAction.get(request.getAction());
        if (action.isEmpty() || !action.get().equals(OrderAction.CREATE)) {
            return false;
        }

        // check for userId and drink
        Order order = request.getOrder();
        return (order != null
                && order.getUserId() != null
                && order.getDrink() != null);
    }

}
