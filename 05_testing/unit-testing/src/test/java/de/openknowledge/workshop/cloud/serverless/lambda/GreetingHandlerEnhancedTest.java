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

import de.openknowledge.workshop.cloud.serverless.util.FakeContext;
import de.openknowledge.workshop.cloud.serverless.model.GreetingRequest;
import de.openknowledge.workshop.cloud.serverless.model.GreetingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


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
public class GreetingHandlerEnhancedTest {

    /**
    static {
        System.setProperty("sl4f2.configurationFile", "lockback.xml");
    }
     */

    GreetingRequestHandler clazzUnderTest;
    FakeContext testContext;

    @BeforeEach
    public void setUp() throws Exception {

        // define class under test
        clazzUnderTest = new GreetingHandlerEnhanced();

        // declare aws lambda test context
        testContext = new FakeContext() {

            // implement all methods of this interface your test(s) will need
            // and setup your test context. For instance, the function name,
            // memory limit and remaining time.

            @Override
            public String getFunctionName() {
                return "handleRequest";
            }

            @Override
            public int getMemoryLimitInMB() {
                return 100;
            }

            @Override
            public int getRemainingTimeInMillis() {
                return 50;
            }

            @Override
            public String getFunctionVersion() {
                return "1.0.0";
            }

            @Override
            public String getAwsRequestId() { return "12345678987654321"; }

            @Override
            public String getLogGroupName() { return "test_log_group"; }

            @Override
            public String getLogStreamName() { return "test_log_stream_12346789"; }



        };
    }

    /**
     * Tests behavior for GreetingHandlerV3.greet(...) method, when
     * - firstName is given
     * - lastName is given
     */
    @ParameterizedTest
    @ValueSource(strings =  {"false", "true"})
    void whenHandelRequestWithFirstNameAndLastName_thenCorrect(String isLoggingEnabled) {

        System.setProperty(GreetingHandlerEnhanced.LOG_CONTEXT, isLoggingEnabled);

        // given
        String firstName = "Lars";
        String lastName = "Roewekamp";
        GreetingRequest request = new GreetingRequest(firstName, lastName);

        // when
        GreetingResponse response = clazzUnderTest.handleRequest(request, testContext);

        // then
        assertEquals("Hello, Lars Roewekamp! i am pleased to meet you.", response.getGreeting());
    }
}
