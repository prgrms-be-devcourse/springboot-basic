package com.example.springbootbasic.parser;

import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.springbootbasic.domain.customer.CustomerStatus.BLACK;
import static com.example.springbootbasic.domain.customer.CustomerStatus.NORMAL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class CsvCustomerParserTest {

    private final CsvCustomerParser customerParser = new CsvCustomerParser();

    @ParameterizedTest(name = "[{index}] csv = {0}, customerStatus = {1}")
    @MethodSource("whenParsingCustomerCsvThenSuccessDummy")
    @DisplayName("csv를 파싱하여 고객 상태 확인 성공")
    void whenParsingCustomerCsvThenSuccessTest(String customerCsv, CustomerStatus customerStatus) {
        Customer customer = customerParser.toCustomerFrom(customerCsv);
        assertThat(customer.getStatus(), is(customerStatus));
    }

    @ParameterizedTest(name = "[{index}] csv = {0}")
    @MethodSource("whenParsingCustomerIdWrongThenSuccessDummy")
    @DisplayName("csv 파싱 중 실패 예외처리")
    void whenParsingCustomerIdWrongThenSuccessTest(String customerCsv) {
        assertThrowsExactly(IllegalArgumentException.class, () -> customerParser.toCustomerFrom(customerCsv));
    }

    static Stream<Arguments> whenParsingCustomerCsvThenSuccessDummy() {
        return Stream.of(
                Arguments.arguments("1 black", BLACK),
                Arguments.arguments("2 black", BLACK),
                Arguments.arguments("3 black", BLACK),
                Arguments.arguments("4 black", BLACK),
                Arguments.arguments("5 black", BLACK),
                Arguments.arguments("1 normal", NORMAL),
                Arguments.arguments("2 normal", NORMAL),
                Arguments.arguments("3 normal", NORMAL),
                Arguments.arguments("4 normal", NORMAL),
                Arguments.arguments("5 normal", NORMAL)
        );
    }

    static Stream<Arguments> whenParsingCustomerIdWrongThenSuccessDummy() {
        return Stream.of(
                Arguments.arguments("a black"),
                Arguments.arguments("1a black"),
                Arguments.arguments("1b black"),
                Arguments.arguments("a1 black"),
                Arguments.arguments("11a black"),
                Arguments.arguments("1a1 black"),
                Arguments.arguments("5 nor123mal", NORMAL),
                Arguments.arguments("5 nor1mal", NORMAL),
                Arguments.arguments("5 norma1", NORMAL)
        );
    }
}