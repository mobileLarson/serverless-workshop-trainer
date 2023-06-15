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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for <code>GreetingHandler</code> using
 * mockito mock of lambda aws context for tests.
 *
 * <ul>
 *     <li>whenHandelRequestWithFirstNameAndLastName_thenCorrect</li>
 * </ul>
 *
 */

public class GreetingHandlerTestMock {

    private static String DEFAULT_GREET = "Hello! I would really like to know, who you are!";


    private Context mockContext;
    private LambdaLogger mockLogger;

    private RequestHandler<GreetingRequest, GreetingResponse> clazzUnderTest;

    @BeforeEach
    void setUp() {
        clazzUnderTest = new GreetingHandler();

        // mock aws lambda logger and all its methods needed
        // for local unit tests
        mockLogger = mock(LambdaLogger.class);
        doNothing().when(mockLogger).log(isA((String.class)));

        // mock aws lambda context and all its methods needed
        // for local unit tests
        mockContext = mock(Context.class);
        when(mockContext.getLogger()).thenReturn(mockLogger);

        /*
        when(mockContext.getFunctionName()).thenReturn("mockFunctionName");
        when(mockContext.getFunctionVersion()).thenReturn("mockFunctionVersion");
        when(mockContext.getAwsRequestId()).thenReturn("awsRequestId");
        */
    }

    /**
     * Tests behavior for GreetingHandler.greet(...) method, when
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
        GreetingResponse response = clazzUnderTest.handleRequest(request, mockContext);

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
        GreetingResponse response = clazzUnderTest.handleRequest(request, mockContext);

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
        GreetingResponse response = clazzUnderTest.handleRequest(request, mockContext);

        // then
        assertEquals(DEFAULT_GREET, response.getGreeting());
    }


}
