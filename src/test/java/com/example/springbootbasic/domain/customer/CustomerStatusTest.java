package com.example.springbootbasic.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.springbootbasic.domain.customer.CustomerStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class CustomerStatusTest {

    @ParameterizedTest(name = "[{index}] input = {0}, customerStatus = {1}")
    @MethodSource("whenFindCustomerStatusThenSuccessDummy")
    @DisplayName("고객 상태 타입을 입력된 문자열로 검색 성공")
    void whenFindCustomerStatusThenSuccessTest(String inputCustomerStatusType, CustomerStatus inputCustomerStatus) {
        CustomerStatus findCustomerStatus = of(inputCustomerStatusType);
        assertThat(findCustomerStatus, is(inputCustomerStatus));
    }


    @ParameterizedTest(name = "[{index}] input = {0}")
    @MethodSource("whenFindCustomerStatusWrongThenExceptionDummy")
    @DisplayName("고객 상태 타입을 잘못 입력된 문자열로 검색 실패 예외처리")
    void whenFindCustomerStatusWrongThenExceptionTest(String inputCustomerStatusType) {
        assertThrowsExactly(IllegalArgumentException.class, () -> CustomerStatus.of(inputCustomerStatusType));
    }

    static Stream<Arguments> whenFindCustomerStatusThenSuccessDummy() {
        return Stream.of(
                Arguments.arguments("normal", NORMAL),
                Arguments.arguments("black", BLACK)
        );
    }

    static Stream<Arguments> whenFindCustomerStatusWrongThenExceptionDummy() {
        return Stream.of(
                Arguments.arguments("normalll"),
                Arguments.arguments("normall"),
                Arguments.arguments("normaall"),
                Arguments.arguments("norma"),
                Arguments.arguments("norm"),
                Arguments.arguments("nor"),
                Arguments.arguments("no"),
                Arguments.arguments("n"),
                Arguments.arguments("blackkk"),
                Arguments.arguments("blackk"),
                Arguments.arguments("blacck"),
                Arguments.arguments("blac"),
                Arguments.arguments("bla"),
                Arguments.arguments("bl"),
                Arguments.arguments("b")
        );
    }

}