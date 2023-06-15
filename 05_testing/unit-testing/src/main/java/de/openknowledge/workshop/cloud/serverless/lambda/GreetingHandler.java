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
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import de.openknowledge.workshop.cloud.serverless.model.GreetingRequest;
import de.openknowledge.workshop.cloud.serverless.model.GreetingResponse;

/**
 * AWS lambda creating a greeting with the help of a given event
 */
// de.openknowledge.workshop.cloud.serverless.lambda.GreetingHandlerV1::handleRequest
public class GreetingHandler implements RequestHandler<GreetingRequest, GreetingResponse> {


    private static String DEFAULT_GREET = "Hello! I would really like to know, who you are!";

    /**
     * Creates a greeting response with the help of a given <code>GreetingRequest</code>
     * representing first name and last name.
     *
     * @param name greeting request representing first name and last name
     * @param context aws lambda context
     * @return greeting response wrapping the created greeting
     */
    public GreetingResponse handleRequest(GreetingRequest name, Context context) {

        LambdaLogger logger = context.getLogger();

        // Print info from the context object
        logger.log("Function name: " + context.getFunctionName());
        logger.log("Max mem allocated: " + context.getMemoryLimitInMB());
        logger.log("Time remaining in milliseconds: " + context.getRemainingTimeInMillis());


        String firstName = name.getFirstName();
        String lastName = name.getLastName();

        logger.log(String.format("firstName: %s", firstName));
        logger.log(String.format("lastName: %s", lastName));

        String greeting = DEFAULT_GREET;

        if (firstName != null && lastName != null) {
            greeting = String.format("Hello, %s %s! i am pleased to meet you.", firstName, lastName);
        }

        logger.log(String.format("greeting: %s", greeting));

        return new GreetingResponse(greeting);
    }

}