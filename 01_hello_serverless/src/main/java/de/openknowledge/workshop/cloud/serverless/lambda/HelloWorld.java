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

import de.openknowledge.workshop.cloud.serverless.model.HelloWorldRequest;
import de.openknowledge.workshop.cloud.serverless.model.HelloWorldResponse;
import de.openknowledge.workshop.cloud.serverless.service.GreetingService;

/**
 * Simplest aws lambda function class possible.
 */
// de.openknowledge.workshop.cloud.serverless.lambda.HelloWorld::greet
public class HelloWorld {

    /**
     * Converts a name (first name, last name) into a greeting
     *
     * @param name Request object of type <code>HelloWorldRequest</code> with first name and last name
     * @param context Lambda function context object of type <code>Context</code>
     * @return Greeting string wrapped inside a response object of type <code>HelloWorldResponse</code>
     */
    // TODO implement static method with name greet
    //    1. see java doc for method signature
    //    2. use GreetingService class and first name / last name from HelloWorldRequest parameter to generate greeting
    //    3. return greeting with the help of HelloWorldResponse class
    //
    // HINT: take a look at the import statements, too.

    public static HelloWorldResponse greet(HelloWorldRequest name, Context context) {

        LambdaLogger logger = context.getLogger();

        logger.log("First name: " + name.getFirstName());
        logger.log("Last name: " + name.getLastName());

        String greetings = GreetingService.greet(name.getFirstName(), name.getLastName());

        logger.log("Response: " + greetings);

        return new HelloWorldResponse(greetings);
    }



}

