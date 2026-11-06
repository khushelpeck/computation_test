package testing.demo.computation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class CalculatorTest {

    private static int count = 0;
    private Calculator calculator;

    @BeforeAll
    static void beforeAll() {
        System.out.println("[Before All] Calculator Test suite starting by khushelpeck");
    }

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        calculator = new Calculator();
        count++;
        System.out.printf("[Before Each] Starting Test #%d: %s%n",
                count, testInfo.getDisplayName());
    }

    // Addition using MethodSource
    @ParameterizedTest(name = "{0} + {1} = {2}")
    @MethodSource("provideAddData")
    void add_twoNumbers(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }

    static Stream<Arguments> provideAddData() {
        return Stream.of(
                Arguments.of(100, 2, 102),
                Arguments.of(100, -2, 98),
                Arguments.of(-100, 2, -98),
                Arguments.of(-100, -2, -102)
        );
    }

    // Subtraction using CsvSource
    @ParameterizedTest(name = "{0} - {1} = {2}")
    @CsvSource({
        "100, 2, 98",
        "100, -2, 102",
        "-100, 2, -102",
        "-100, -2, -98"
    })
    void subtract_twoNumbers(int a, int b, int expected) {
        assertEquals(expected, calculator.substract(a, b));
    }

    // Multiplication using CsvFileSource
    @ParameterizedTest(name = "{0} * {1} = {2}")
    @CsvFileSource(resources = "/data/multiple.csv")
    void multiply_twoNumbers(int a, int b, int expected) {
        assertEquals(expected, calculator.multiple(a, b));
    }

    // Negative test for division
    @Test
    @DisplayName("Divide by zero throws IllegalArgumentException")
    void divide_byZero() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.divide(10, 0));
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        System.out.printf("[After Each] Finished Test #%d: %s%n",
                count, testInfo.getDisplayName());
    }

    @AfterAll
    static void afterAll() {
        System.out.printf("[After All] Completed %d test invocations by khushelpeck.%n",
                count);
    }
}
