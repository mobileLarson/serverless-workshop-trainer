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
import de.openknowledge.workshop.cloud.serverless.service.GreetingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// de.openknowledge.workshop.cloud.serverless.lambda.GreetingHandler::handleRequest
public class GreetingHandler implements RequestHandler<GreetingRequest, GreetingResponse> {

    public GreetingResponse handleRequest(GreetingRequest name, Context context) {

        LambdaLogger logger = context.getLogger();

        String firstName = name.getFirstName();
        String lastName = name.getLastName();

        logger.log(String.format("INPUT firstName: %s \n", firstName));
        logger.log(String.format("INPUT lastName: %s \n", lastName));

        String greeting = GreetingService.greet(firstName, lastName);

        logger.log(String.format("OUTPUT greeting: %s \n" ,greeting));

        return new GreetingResponse(greeting);
    }

}