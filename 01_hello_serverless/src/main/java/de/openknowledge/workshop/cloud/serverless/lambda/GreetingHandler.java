package de.openknowledge.workshop.cloud.serverless.lambda;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * AWS Lambda greeting handler.
 *
 * Converts a string based input name into a corresponding greeting
 * with the help of an AWS lambda function.
 */
// de.openknowledge.workshop.cloud.serverless.lambda.GreetingHandler::greet
public class GreetingHandler {

    /**
     * Converts a string based input name into a
     * corresponding greeting.
     *
     * @param name string based input name
     * @param context AWS lambda context
     * @return greeting, generated for input name
     */
    public String greet(String name, Context context) {
        return String.format("Hello, %s", name);
    }
}
