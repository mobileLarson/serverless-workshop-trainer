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
package de.openknowledge.workshop.serverless.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.openknowledge.workshop.serverless.model.Order;

import de.openknowledge.workshop.serverless.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.openknowledge.workshop.serverless.util.Environment.getEnvVarAsBoolean;

/**
 * AWS lambda creating an order with the help of a given event
 */
// de.openknowledge.workshop.serverless.lambda.OrderHandler::handleRequest
public class OrderHandler implements RequestHandler<OrderRequest, OrderResponse> {

    private static final Logger logger = LoggerFactory.getLogger(OrderHandler.class);

    private final static String ENV_AUDIT = "AUDIT_ENABLED";

    /**
     * Creates an order response with the help of a given <code>OrderRequest</code>
     * representing the ordering user and the drink to create.
     *
     * @param orderRequest order event (userId, drink)
     * @param context aws lambda context
     * @return order response wrapping the created order
     */
    public OrderResponse handleRequest(OrderRequest orderRequest, Context context) {

        Boolean isAuditEnabled = getEnvVarAsBoolean(ENV_AUDIT, Boolean.FALSE);
        OrderResponse orderResponse;

        // audit incoming data before any manipulation
        if (isAuditEnabled)
            auditRequest(orderRequest, context);

        // TODO implement business logic for ordering a coffee
        //    1. extract values from order request (make sure request is valid)
        //    2. call order service
        //    3. return info about created order
        //
        // HINT: take a look at the import statements, too.

        if (orderRequest.isValid()) {
            String userToServeOrder = orderRequest.getUserId();
            String drinkToOrder = orderRequest.getDrink();

            Order orderCreated = OrderService.createOrder(userToServeOrder, drinkToOrder);
            orderResponse = new OrderResponse(orderCreated);
        } else {
            // orderResponse = OrderResponse.emptyResponse();
            throw new IllegalArgumentException("ERROR: missing attributes (userId and/or drink)");
        }

        // audit outgoing data after all manipulation
        if (isAuditEnabled)
            auditResponse(orderResponse);

        return orderResponse;
    }

    //--- PRIVATE METHODS

    private void auditRequest(OrderRequest input, Context context) {
        logger.info(String.format("Request: %s", input));
    }

    private void auditResponse(OrderResponse output) {
        logger.info(String.format("Response: %s", output));
    }

}