package de.openknowledge.workshop.cloud.serverless.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for <code>GreetingService</code>.
 *
 * <ul>
 *     <li>whenGreetWithFirstNameAndLastName_thenCorrect</li>
 *     <li>whenGreetWithoutFirstName_thenInCorrect</li>
 *     <li>whenGreetWithoutLastName_thenInCorrect</li>
 * </ul>
 *
 */
public class GreetingServiceTest {

    private static String DEFAULT_GREET = "Hello! I would really like to know, who you are!";


    /**
     * Tests behavior for business logic, when
     * - firstName is given
     * - lastName is given
     */
    @Test
    void whenGreetWithFirstNameAndLastName_thenCorrect() {

        // given
        String firstName = "Lars";
        String lastName = "Roewekamp";

        // when
        String greetResult = GreetingService.greet(firstName, lastName);

        // then
        assertEquals("Hello, Lars Roewekamp! i am pleased to meet you.", greetResult);
    }

    /**
     * TTests behavior for business logic, when
     * - firstName is  given
     * - lastName is missing
     */
    @Test
    void whenGreetWithoutFirstName_thenInCorrect() {

        // given
        String lastName = "Roewekamp";

        // when
        String greetResult = GreetingService.greet(null, lastName);

        // then
        assertEquals(DEFAULT_GREET, greetResult);

    }

    /**
     * Tests behavior for business logic, when
     * - firstName is missing
     * - lastName is given
     */
    @Test
    void whenGreetWithoutLastName_thenInCorrect() {

        // given
        String firstName = "Lars";

        // when
        String greetResult = GreetingService.greet(firstName, null);

        // then
        assertEquals(DEFAULT_GREET, greetResult);
    }

}
