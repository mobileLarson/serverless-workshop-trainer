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
package de.openknowledge.workshop.cloud.serverless.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonIOException;

import de.openknowledge.workshop.cloud.serverless.model.ErrorResponse;
import de.openknowledge.workshop.cloud.serverless.model.GreetingRequest;
import de.openknowledge.workshop.cloud.serverless.model.GreetingResponse;
import de.openknowledge.workshop.cloud.serverless.service.GreetingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// de.openknowledge.workshop.cloud.serverless.lambda.GreetingHandlerV2::handleRequest
public class GreetingHandlerV2 implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Logger logger = LoggerFactory.getLogger(GreetingHandlerV2.class);

    private static final String ERROR_MISSING_PARAMETERS = "Missing or incorrect parameters. Expected 'firstName' and 'lastName'.";

    private final Gson gson = new Gson();

    /**
     *
     * @param requestEvent
     * @param context
     * @return
     */
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {

        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

        try {
            //fetching the value send in the request body

            String message = requestEvent.getBody();
            GreetingRequest request = gson.fromJson(message, GreetingRequest.class);
            GreetingResponse response = handleGreetingRequest(request, context);

            //setting up the response message
            responseEvent.setBody(gson.toJson(response, GreetingResponse.class));
            responseEvent.setStatusCode(200);
            return responseEvent;
        } catch (IllegalArgumentException iae) {
            // set status code to 422 = PRECONDITION FAILED
            ErrorResponse errorResponse = new ErrorResponse(422, iae.getMessage());
            responseEvent.setBody(gson.toJson(errorResponse, ErrorResponse.class));
            responseEvent.setStatusCode(422);
            return responseEvent;
        } catch(JsonSyntaxException | JsonIOException jex) {
            ErrorResponse errorResponse = new ErrorResponse(400, "JSON Syntax or IO exception");
            responseEvent.setBody(gson.toJson(errorResponse, ErrorResponse.class));
            responseEvent.setStatusCode(400);
            return responseEvent;
        } catch(Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse(400, ex.getMessage());
            ex.printStackTrace();
            responseEvent.setBody(gson.toJson(errorResponse, ErrorResponse.class));
            responseEvent.setStatusCode(400);
            return responseEvent;
        }
    }

    /**
     *
     * @param name
     * @param context
     * @return
     */
    private GreetingResponse handleGreetingRequest(GreetingRequest name, Context context) throws IllegalArgumentException {


        if (name != null && name.isValid()) {
            String firstName = name.getFirstName();
            String lastName = name.getLastName();

            logger.info(String.format("INPUT firstName: %s", firstName));
            logger.info(String.format("INPUT lastName: %s", lastName));

            String greeting = GreetingService.greet(firstName, lastName);

            logger.info(String.format("OUTPUT greeting: %s" ,greeting));

            return new GreetingResponse(greeting);
        } else {
            throw new IllegalArgumentException(ERROR_MISSING_PARAMETERS);
        }
    }



}
