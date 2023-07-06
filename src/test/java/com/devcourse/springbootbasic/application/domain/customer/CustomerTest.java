package com.devcourse.springbootbasic.application.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

    static Stream<Arguments> provideCustomers() {
        return Stream.of(
                Arguments.of(
                        UUID.fromString("061d89ad-1a6a-11ee-aed4-0242ac110002"),"사과","apple@gmail.com",LocalDateTime.parse("2023-07-04T12:55:16.649111", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnn")),
                        new Customer(UUID.fromString("061d89ad-1a6a-11ee-aed4-0242ac110002"),"사과","apple@gmail.com",LocalDateTime.parse("2023-07-04T12:55:16.649111", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnn")))),
                Arguments.of(UUID.fromString("06201b27-1a6a-11ee-aed4-0242ac110002"),"딸기","strawberry@gmail.com",LocalDateTime.parse("2023-07-04T12:55:16.668111", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnn")),
                        new Customer(UUID.fromString("06201b27-1a6a-11ee-aed4-0242ac110002"),"딸기","strawberry@gmail.com",LocalDateTime.parse("2023-07-04T12:55:16.668111", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnn")))),
                Arguments.of(UUID.fromString("06223606-1a6a-11ee-aed4-0242ac110002"),"포도","grape@gmail.com",LocalDateTime.parse("2023-07-04T12:55:16.682111", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnn")),
                    new Customer(UUID.fromString("06223606-1a6a-11ee-aed4-0242ac110002"),"포도","grape@gmail.com",LocalDateTime.parse("2023-07-04T12:55:16.682111", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnn")))),
                Arguments.of(UUID.fromString("06223606-1a6a-11ee-aed4-0242ac110003"),"배","peach@gmail.com", LocalDateTime.parse("2023-05-23T12:42:12.121111", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnn")),
                        new Customer(UUID.fromString("06223606-1a6a-11ee-aed4-0242ac110003"),"배","peach@gmail.com", LocalDateTime.parse("2023-05-23T12:42:12.121111", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnn"))))
        );
    }

    @ParameterizedTest
    @DisplayName("고객 정보가 출력되면 성공한다.")
    @MethodSource("provideCustomers")
    void ToString_Customer_ReturnCustomerString(UUID customerId, String name, String email, LocalDateTime createdAt, Customer customer) {
        var expected = MessageFormat.format(
                "Customer(id: {0}, name: {1}, email: {2}, createAt: {3})"
                , customerId, name, email, createdAt);
        var result = customer.toString();
        assertEquals(expected, result);
    }

}