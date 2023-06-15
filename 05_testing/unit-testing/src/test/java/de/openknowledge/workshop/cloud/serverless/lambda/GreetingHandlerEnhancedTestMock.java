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
import de.openknowledge.workshop.cloud.serverless.model.GreetingRequest;
import de.openknowledge.workshop.cloud.serverless.model.GreetingResponse;
import de.openknowledge.workshop.cloud.serverless.util.LambdaEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for <code>GreetingHandlerV3</code> using
 * mockito mock of lambda aws context for tests.
 *
 * <ul>
 *     <li>whenHandelRequestWithFirstNameAndLastName_thenCorrect</li>
 * </ul>
 *
 */

public class GreetingHandlerEnhancedTestMock {

    private Context mockContext;
    private GreetingRequestHandler clazzUnderTest;

    @BeforeEach
    void setUp() {
        clazzUnderTest = new GreetingHandlerEnhanced();

        // mock aws lambda context and all its methods needed
        // for local unit tests
        mockContext = mock(Context.class);
        when(mockContext.getFunctionName()).thenReturn("handleRequest");
        when(mockContext.getMemoryLimitInMB()).thenReturn(100);
        when(mockContext.getRemainingTimeInMillis()).thenReturn(50);
    }

    /**
     * Tests behavior for GreetingHandlerEnhanced.greet(...) method, when
     * - firstName is given
     * - lastName is given
     *
     * 2 runs to mock environment var as FALSE and TRUE
     */
    @ParameterizedTest
    @ValueSource(booleans =  {false, true})
    void whenHandelRequestWithFirstNameAndLastName_thenCorrect(boolean isLoggingEnabled) {

        try (MockedStatic<LambdaEnvironment> mockLambdaEnvironment = Mockito.mockStatic(LambdaEnvironment.class)) {

            mockLambdaEnvironment.when( () -> LambdaEnvironment.getEnvVarAsBoolean(eq("logContext"), any()) ).thenReturn(isLoggingEnabled);


            // given
            String firstName = "Lars";
            String lastName = "Roewekamp";
            GreetingRequest request = new GreetingRequest(firstName, lastName);

            // when
            GreetingResponse response = clazzUnderTest.handleRequest(request, mockContext);

            // then
            assertEquals("Hello, Lars Roewekamp! i am pleased to meet you.", response.getGreeting());
       }
    }
}
