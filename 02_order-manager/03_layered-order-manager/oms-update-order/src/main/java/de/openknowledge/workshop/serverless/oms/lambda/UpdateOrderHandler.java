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
import de.openknowledge.workshop.serverless.oms.model.OrderStatus;
import de.openknowledge.workshop.serverless.oms.service.UpdateOrderService;
import de.openknowledge.workshop.serverless.oms.util.OmsException;

/**
 * AWS lambda creating an order with the help of a given event
 */
// de.openknowledge.workshop.serverless.oms.lambda.UpdateOrderHandler::handleRequest
public class UpdateOrderHandler implements RequestHandler<UpdateOrderRequest, UpdateOrderResponse> {

    private final static String LOG_CONTEXT = "logContext";

    /**
     * Creates an order response with the help of a given <code>OrderRequest</code>
     * representing the ordering user and the drink to create.
     *
     * @param updateOrderRequest order event (userId, drink)
     * @param context aws lambda context
     * @return order response wrapping the created order
     */
    public UpdateOrderResponse handleRequest(UpdateOrderRequest updateOrderRequest, Context context) {

        UpdateOrderResponse response;

        if (updateOrderRequest.isValid()) {
            try {
                Order order = UpdateOrderService.updateOrder(updateOrderRequest.getOrder());
                return new UpdateOrderResponse(order, "OK", "Order successfully updated.");
            } catch (Exception ex) {
                throw new OmsException("ERROR: A problem occurred during order deletion. See 'cause' for more details.", ex);
            }
        } else {
            throw new IllegalArgumentException(String.format("ERROR : Missing attributes (orderId)."));
        }
    }
}