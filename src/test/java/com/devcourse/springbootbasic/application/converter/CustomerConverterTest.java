package com.devcourse.springbootbasic.application.converter;

import com.devcourse.springbootbasic.application.domain.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

class CustomerConverterTest {

    @Test
    @DisplayName("고객 리스트를 문자열 리스트로 변환하면 성공")
    void testConvertToStringList() {
        List<Customer> customers = List.of(
                new Customer(0, "사과"),
                new Customer(1, "딸기"),
                new Customer(2, "포도"),
                new Customer(3, "배")
        );
        var result = CustomerConverter.convertToStringList(customers);
        assertThat(result, notNullValue());
        assertThat(result, not(empty()));
        assertThat(result, instanceOf(List.class));
        assertThat(result.get(0), instanceOf(String.class));
    }

    @ParameterizedTest
    @DisplayName("고객정보Csv 문자열을 고객 클래스로 변환 시 성공")
    @CsvSource(value = {"0,사과:사과", "1,딸기:딸기", "2,포도:포도", "3,배:배"}, delimiter = ':')
    void convertCsvToCustomer(String input, String expected) {
        var result = CustomerConverter.convertCsvToCustomer(input);
        assertThat(result, notNullValue());
        assertThat(result, instanceOf(Customer.class));
        assertThat(result.getName(), is(expected));
    }
}