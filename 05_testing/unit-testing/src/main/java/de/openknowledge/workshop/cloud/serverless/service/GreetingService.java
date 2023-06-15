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
package de.openknowledge.workshop.cloud.serverless.service;

import org.apache.commons.lang3.StringUtils;

/**
 * Service to create a greeting
 */
public class GreetingService {

    // default greeting
    private static String DEFAULT_GREET = "Hello! I would really like to know, who you are!";

    /**
     * Generates greeting string from full name string as "lastName, firstName"
     *
     * @param fullName full name string of the requested pattern
     * @return greeting for the given name
     */
    public static String greet(String fullName) {

        String greetings = DEFAULT_GREET;

        if (!StringUtils.isBlank(fullName)) {
            String[] names = fullName.split(" ");
            if (names.length == 2) {
                greetings = greet(names[0].trim(), names[1].trim());
            }
        }
        return greetings;
    }

    /**
     * Generates greeting string from first name and last name
     *
     * @param firstName first name
     * @param lastName  first name
     * @return greeting for the given name
     */
    public static String greet(String firstName, String lastName) {

        String greeting = DEFAULT_GREET;

        if (firstName != null && lastName != null) {
            greeting = String.format("Hello, %s %s! i am pleased to meet you.", firstName, lastName);
        }

        return greeting;
    }
}