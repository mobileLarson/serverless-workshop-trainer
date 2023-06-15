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
import de.openknowledge.workshop.cloud.serverless.util.StaticGreeter;

import static de.openknowledge.workshop.cloud.serverless.util.LambdaContextLogger.logContext;
import static de.openknowledge.workshop.cloud.serverless.util.LambdaEnvironment.getEnvVarAsBoolean;

/**
 * AWS lambda creating a greeting with the help of a given event
 */
// de.openknowledge.workshop.cloud.serverless.lambda.GreetingHandlerV3::handleRequest
public class GreetingHandlerEnhanced implements GreetingRequestHandler {

    public final static String LOG_CONTEXT = "logContext";

    /**
     * Creates a greeting response with the help of a given <code>GreetingRequest</code>
     * representing first name and last name.
     *
     * @param name greeting request representing first name and last name
     * @param context aws lambda context
     * @return greeting response wrapping the created greeting
     */
    public GreetingResponse handleRequest(GreetingRequest name, Context context) {

        if (getEnvVarAsBoolean(LOG_CONTEXT, Boolean.FALSE)) {
            logContext(context);
        }

        String greeting = GreetingService.greet(name.getFirstName(), name.getLastName());
        return new GreetingResponse(greeting);
    }



}