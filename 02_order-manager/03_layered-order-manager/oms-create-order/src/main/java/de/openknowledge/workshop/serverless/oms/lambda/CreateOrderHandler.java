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
package de.openknowledge.workshop.serverless.oms.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import de.openknowledge.workshop.serverless.oms.model.Order;
import de.openknowledge.workshop.serverless.oms.service.CreateOrderService;
import de.openknowledge.workshop.serverless.oms.util.OmsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AWS lambda creating an order with the help of a given event
 */
// de.openknowledge.workshop.serverless.oms.lambda.CreateOrderHandler::handleRequest
public class CreateOrderHandler implements RequestHandler<CreateOrderRequest, CreateOrderResponse> {

    private static final Logger logger = LoggerFactory.getLogger(CreateOrderHandler.class);

    /**
     * Creates an order response with the help of a given <code>OrderRequest</code>
     * representing the ordering user and the drink to create.
     *
     * @param createOrderRequest order event (userId, drink)
     * @param context aws lambda context
     * @return order response wrapping the created order
     */
    public CreateOrderResponse handleRequest(CreateOrderRequest createOrderRequest, Context context) {

        CreateOrderResponse response;

        if (createOrderRequest.isValid()) {

            try {
                Order order = CreateOrderService.createOrder(createOrderRequest.getUserId(), createOrderRequest.getDrink());
                return new CreateOrderResponse(order, "OK", "Order successfully created.");
            } catch(Exception ex) {
                throw new OmsException("ERROR: A problem occurred during order creation. See 'cause' for more details.", ex);
            }
        } else {
            throw new IllegalArgumentException(String.format("ERROR : Missing attributes (userId and/or drink)."));
        }
    }
}