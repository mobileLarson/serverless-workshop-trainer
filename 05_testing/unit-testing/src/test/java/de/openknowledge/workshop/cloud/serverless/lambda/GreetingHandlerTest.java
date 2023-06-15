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

import com.amazonaws.services.lambda.runtime.*;
import de.openknowledge.workshop.cloud.serverless.util.FakeContext;
import de.openknowledge.workshop.cloud.serverless.util.FakeLambdaLogger;
import de.openknowledge.workshop.cloud.serverless.model.GreetingRequest;
import de.openknowledge.workshop.cloud.serverless.model.GreetingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for <code>GreetingHandler</code> using
 * <code>TestContext</code> for tests.
 *
 * <ul>
 *     <li>whenHandelRequestWithFirstNameAndLastName_thenCorrect</li>
 * </ul>
 *
 */
public class GreetingHandlerTest {

    private static String DEFAULT_GREET = "Hello! I would really like to know, who you are!";

    RequestHandler<GreetingRequest, GreetingResponse> clazzUnderTest;

    Context fakeContext;
    LambdaLogger fakeLambdaLogger;

    @BeforeEach
    public void setUp() throws Exception {

        // define class under test
        clazzUnderTest = new GreetingHandler();

        fakeLambdaLogger = new FakeLambdaLogger() {

            // TODO
            // implement all methods of LambdaLogger the local test(s) will need.
            @Override
            public void log(String logString) {
                // no implementation by purpose
            }
        };

        // declare aws lambda test context
        fakeContext = new FakeContext() {

            // implement all methods of TestContext the local test(s) will need.
            // For instance, the function name, memory limit and remaining time.

            // TODO
            @Override
            public String getFunctionName() {
                return "functionName";
            }

            @Override
            public String getFunctionVersion() {
                return "functionVersion";
            }

            @Override
            public String getAwsRequestId() {
                return "awsRequestId";
            }

            @Override
            public LambdaLogger getLogger() {
                return fakeLambdaLogger;
            }

            @Override
            public String getLogGroupName() {
                return "context::getLogGroupName";
            }

            @Override
            public String getLogStreamName() {
                return "context::getLogStreamName";
            }


            public String getInvokedFunctionArn() {
                return "context::getInvokedFunctionArn";
            }

            @Override
            public CognitoIdentity getIdentity() {
                return null;
            }

            @Override
            public ClientContext getClientContext() {
                return new ClientContext() {
                    @Override
                    public Client getClient() {
                        return null;
                    }

                    @Override
                    public Map<String, String> getCustom() {
                        return null;
                    }

                    @Override
                    public Map<String, String> getEnvironment() {
                        return null;
                    }
                };
            }

            @Override
            public int getRemainingTimeInMillis() {
                return 0;
            }

            @Override
            public int getMemoryLimitInMB() {
                return 0;
            }

        };
    }

    /**
     * Tests behavior for GreetingHandlerV3.greet(...) method, when
     * - firstName is given
     * - lastName is given
     */
    @Test
    void whenHandelRequestWithFirstNameAndLastName_thenCorrect() {

        // given
        String firstName = "Lars";
        String lastName = "Roewekamp";
        GreetingRequest request = new GreetingRequest(firstName, lastName);

        // when
        GreetingResponse response = clazzUnderTest.handleRequest(request, fakeContext);

        // then
        assertEquals("Hello, Lars Roewekamp! i am pleased to meet you.", response.getGreeting());
    }

    /**
     * Tests behavior for GreetingService.greet(...) handler method, when
     * - greetingRequest.firstName is given
     * - greetingRequest.lastName is missing
     */
    @Test
    void whenGreetWithoutFirstName_thenInCorrect() {

        // given
        String lastName = "Roewekamp";
        GreetingRequest request = new GreetingRequest(null, lastName);

        // when
        GreetingResponse response = clazzUnderTest.handleRequest(request, fakeContext);

        // then
        assertEquals(DEFAULT_GREET, response.getGreeting());

    }

    /**
     * Tests behavior for GreetingService.greet(...) handler method, when
     * - greetingRequest.firstName is given
     * - greetingRequest.lastName is missing
     */
    @Test
    void whenGreetWithouLastName_thenInCorrect() {

        // given
        String firstName = "Lars";
        GreetingRequest request = new GreetingRequest(firstName, null);

        // when
        GreetingResponse response = clazzUnderTest.handleRequest(request, fakeContext);

        // then
        assertEquals(DEFAULT_GREET, response.getGreeting());
    }

}
