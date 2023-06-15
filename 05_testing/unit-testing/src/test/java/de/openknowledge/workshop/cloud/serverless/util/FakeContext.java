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

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.apache.commons.lang3.NotImplementedException;

/**
 * Aws Lambda Context implementation for testing purpose
 */
public class FakeContext implements Context {

    public String getAwsRequestId() {
        throw new NotImplementedException("context::getAwsRequestId");
    }

    public String getLogGroupName() {
        throw new NotImplementedException("context::getLogGroupName");
    }

    public String getLogStreamName() {
        throw new NotImplementedException("context::getLogStreamName");
    }

    public String getFunctionName() {
        throw new NotImplementedException("context::getFunctionName");
    }

    public String getFunctionVersion() {
        throw new NotImplementedException("context::getFunctionVersion");
    }

    public String getInvokedFunctionArn() {
        throw new NotImplementedException("context::getInvokedFunctionArn");
    }

    public CognitoIdentity getIdentity() {
        throw new NotImplementedException("context::getIdentity");
    }

    public ClientContext getClientContext() {
        throw new NotImplementedException("context::getClientContext");
    }

    public int getRemainingTimeInMillis() {
        throw new NotImplementedException("context::getRemainingTimeInMillis");
    }

    public int getMemoryLimitInMB() {
        throw new NotImplementedException("context::getMemoryLimitInMB");
    }

    public LambdaLogger getLogger() {
        throw new NotImplementedException("context::getLogger");
    }
}
