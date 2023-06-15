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


import de.openknowledge.workshop.serverless.oms.model.OrderStatus;

/**
 * Wrapper object for order
 */
public class DeleteOrderResponse {

    private String status;
    private String message;

    public DeleteOrderResponse() {
        // empty by default
    }

    public DeleteOrderResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static DeleteOrderResponse emptyResponse() {
        return emptyResponse("", "");
    }

    public static DeleteOrderResponse emptyResponse(String status, String message) {
        DeleteOrderResponse response = new DeleteOrderResponse();
        response.setStatus(status);
        response.setMessage(message);
        return response;
    }
}