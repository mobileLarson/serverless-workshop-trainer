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

import com.amazonaws.services.lambda.runtime.Context;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lambda context logger
 */
public class LambdaContextLogger {

    private static final Logger logger = LoggerFactory.getLogger(LambdaContextLogger.class);

    /**
     * logs lambda context
     * @param context context to log
     */
    public static void logContext(Context context) {

        logger.info(String.format("Function name: %s", context.getFunctionName()));
        logger.info(String.format("Function version: %s",  context.getFunctionVersion()));

        logger.info(String.format("AWS RequestId: %s", context.getAwsRequestId()));

        logger.info(String.format("Max mem allocated: %d" , context.getMemoryLimitInMB()));
        logger.info(String.format("Time remaining in milliseconds: %d", context.getRemainingTimeInMillis()));

        logger.info(String.format("Log Group: %s",context.getLogGroupName()));
        logger.info(String.format("Log Stream: %s",context.getLogStreamName()));

        Map<String, String> env = System.getenv();

        for (Map.Entry<String, String> envEntry : env.entrySet()) {
            logger.info(String.format("Env %s: %s",envEntry.getKey(), envEntry.getValue()));
        }

    }

}
