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
package de.openknowledge.workshop.cloud.serverless.util;

import java.util.Map;

/**
 * Clazz to access system environment variable values in a type safe way.
 */
public class LambdaEnvironment {

    // default value for string system environment variable values
    private static final String DEFAULT_STRING = "";

    // default value for boolean system environment variable values
    private static final Boolean DEFAULT_BOOLEAN = Boolean.FALSE;

    // default value for double system environment variable values
    private static final Double DEFAULT_DOUBLE = 0.00;

    /**
     * Converts a system environment variable value to type String
     *
     * @param key unique key of the environment variable
     * @param defaultValue default value to return if given variable could not be found
     * @return string value representation of a system environment variable , if present
     *         Default value, else
     */
    public static String getEnvVarAsString(String key, String defaultValue) {
        Map<String, String> environment = System.getenv();
        String value = environment.get(key);
        value = value != null? value : defaultValue;
        return value;
    }

    /**
     * Converts a system environment variable value to type String
     *
     * @param key unique key of the environment variable
     * @return string value representation of a system environment variable
     */
    public static String getEnvVarAsString(String key) {
        return getEnvVarAsString(key, DEFAULT_STRING);
    }

    /**
     * Converts a system environment variable value to type Boolean
     *
     * @param key unique key of the environment variable
     * @param defaultValue default value to return if given variable could not be found
     * @return boolean representation of the environment variable, if present
     *         Default value, else
     */
    public static Boolean getEnvVarAsBoolean(String key, Boolean defaultValue) {
        Map<String, String> environment = System.getenv();
        String value = environment.get(key);
        return value != null? Boolean.valueOf(value) : defaultValue;
    }

    /**
     * Converts a system environment variable value to type Boolean
     *
     * @param key unique key of the environment variable
     * @return boolean representation of the environment variable
     */
    public static Boolean getEnvVarAsBoolean(String key) {
        return getEnvVarAsBoolean(key, DEFAULT_BOOLEAN);
    }

    /**
     * Converts an environment variable to Double value
     *
     * @param key unique key of the environment variable
     * @param defaultValue  default value to use, if key is not present
     * @return Double value representation of the environment variable, if present
     *         Default value, else
     */
    public static double getEnvVarAsDouble(String key, double defaultValue) {

        double returnValue = defaultValue;

        Map<String, String> environment = System.getenv();
        String value = environment.get(key);

        if (value != null && !value.equals("")) {
            try {
                returnValue = Double.parseDouble(value);
            } catch (NumberFormatException ex) {
                // environment variable is present but has wrong format
                // nothing to do, cause return value is already set to default value
            }
        }
        return returnValue;
    }

    /**
     * Converts an environment variable to Double value
     *
     * @param key unique key of the environment variable
     * @return Double value representation of the environment variable, if present
     *         Default value, else
     */
    public static double getEnvVarAsDouble(String key) {
        return getEnvVarAsDouble(key, DEFAULT_DOUBLE);
    }

}
