package com.devcourse.springbootbasic.application.converter;

import com.devcourse.springbootbasic.application.domain.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class CustomerConverterTest {

    @Test
    @DisplayName("고객 리스트를 문자열 리스트로 변환하면 성공한다.")
    void ConvertToStringList_CustomerParam_ReturnCustomerString() {
        List<Customer> customers = List.of(
                new Customer(UUID.fromString("061d89ad-1a6a-11ee-aed4-0242ac110002"),"사과","apple@gmail.com", LocalDateTime.parse("2023-07-04 12:55:16.649", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))),
                new Customer(UUID.fromString("06201b27-1a6a-11ee-aed4-0242ac110002"),"딸기","strawberry@gmail.com",LocalDateTime.parse("2023-07-04 12:55:16.668", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))),
                new Customer(UUID.fromString("06223606-1a6a-11ee-aed4-0242ac110002"),"포도","grape@gmail.com",LocalDateTime.parse("2023-07-04 12:55:16.682", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))),
                new Customer(UUID.fromString("06223606-1a6a-11ee-aed4-0242ac110003"),"배","peach@gmail.com", LocalDateTime.parse("2023-05-23 12:42:12.121", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
        );
        var result = CustomerConverter.convertToStringList(customers);
        assertThat(result, notNullValue());
        assertThat(result, not(empty()));
        assertThat(result, instanceOf(List.class));
        assertThat(result.get(0), instanceOf(String.class));
    }

    @ParameterizedTest
    @DisplayName("고객정보Csv 문자열을 고객 클래스로 변환 시 성공한다.")
    @MethodSource("provideCustomerCsv")
    void ConvertCsvToCustomer_CsvParam_ReturnCustomer(String expected, String input) {
        var result = CustomerConverter.convertCsvToCustomer(input);
        assertThat(result, notNullValue());
        assertThat(result, instanceOf(Customer.class));
        assertThat(result.getName(), is(expected));
    }

    static Stream<Arguments> provideCustomerCsv() {
        return Stream.of(
    Arguments.of("tester00","061d89ad-1a6a-11ee-aed4-0242ac110002,tester00,test00@gmail.com,2023-07-04 12:55:16.649"),
            Arguments.of("tester01","06201b27-1a6a-11ee-aed4-0242ac110002,tester01,test01@gmail.com,2023-07-04 12:55:16.668"),
            Arguments.of("tester02","06223606-1a6a-11ee-aed4-0242ac110002,tester02,test02@gmail.com,2023-07-04 12:55:16.682")
        );
    }
}