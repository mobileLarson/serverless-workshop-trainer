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
import de.openknowledge.workshop.cloud.serverless.model.GreetingRequest;
import de.openknowledge.workshop.cloud.serverless.model.GreetingResponse;
import de.openknowledge.workshop.cloud.serverless.service.GreetingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * AWS lambda creating a greeting with the help of a given event
 */
// de.openknowledge.workshop.cloud.serverless.lambda.GreetingHandlerV2::handleRequest
public class GreetingHandlerV2 implements GreetingHandler {

    private static final Logger logger = LoggerFactory.getLogger(GreetingHandlerV2.class);

    /**
     * Creates a greeting response with the help of a given <code>GreetingRequest</code>
     * representing first name and last name.
     *
     * @param name greeting request representing first name and last name
     * @param context aws lambda context
     * @return greeting response wrapping the created greeting
     */
    public GreetingResponse handleRequest(GreetingRequest name, Context context) {

        logContextInfo(context);

        String greeting = GreetingService.greet(name.getFirstName(), name.getFirstName());
        return new GreetingResponse(greeting);
    }

    /**
     * logs aws lambda context information
     * @param context aws lambda context
     */
    private void logContextInfo(Context context) {
        // Print info from the context object
        logger.info(String.format("Function name: %s", context.getFunctionName()));
        logger.info(String.format("Max mem allocated: %d", context.getMemoryLimitInMB()));
        logger.info(String.format("Time remaining in milliseconds: %d",context.getRemainingTimeInMillis()));

    }
}
