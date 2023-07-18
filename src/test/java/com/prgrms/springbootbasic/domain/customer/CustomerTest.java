package com.prgrms.springbootbasic.domain.customer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CustomerTest {

    //해피 케이스 테스트 - Hamcrest 테스트
    @Test
    @DisplayName("고객 생성 테스트")
    void createCustomerTest() {
        //given
        UUID customerId = UUID.randomUUID();
        String name = "Hanjo";
        String email = "Hanjo@example.com";
        LocalDateTime createdAt = LocalDateTime.now();

        //when
        Customer customer = new Customer(customerId, name, email, createdAt);

        //then
        assertThat(customer.getCustomerId(), is(equalTo(customerId)));
        assertThat(customer.getName(), is(equalTo(name)));
        assertThat(customer.getEmail(), is(equalTo(email)));
        assertThat(customer.getCreateAt(), is(equalTo(createdAt)));

    }

    //엣지 케이스 테스트 - Nested 클래스 사용
    // 정상적으로 영문 Name이 입력되었을 때 테스트, 영문 이외의 글자가 Name에 들어왔을 때 테스트
    @Nested
    @DisplayName("고객 Name의 Validation 확인 테스트")
    class NameValidationTest {

        @Test
        @DisplayName("정상적으로 영문 Name이 입력되었을 때 테스트")
        void validNameTest() {
            String name = "JohnDoe";

            assertThat(new Customer(UUID.randomUUID(), name, "valid@example.com", LocalDateTime.now()).getName(), is(equalTo(name)));
        }

        @Test
        @DisplayName("영문 이외의 글자가 Name에 들어왔을 때 테스트")
        void invalidNameTest() {
            String name = "잘못된이름";

            assertThrows(IllegalArgumentException.class, () -> new Customer(UUID.randomUUID(), name, "invalid@example.com", LocalDateTime.now()));
        }
    }

    //엣지 케이스 테스트 - Nested 클래스 사용
    // 정상적으로 Email이 입력되었을 때 테스트, 비정상적인 Email이 들어왔을 때 테스트
    @Nested
    @DisplayName("고객 Email의 Validation 테스트")
    class EmailValidationTest {

        @Test
        @DisplayName("정상적으로 Email이 입력되었을 때 테스트")
        void validEmailTest() {
            String email = "valid@example.com";

            assertThat(new Customer(UUID.randomUUID(), "ValidName", email, LocalDateTime.now()).getEmail(), is(equalTo(email)));
        }

        @Test
        @DisplayName("비정상적인 Email이 들어왔을 때 테스트")
        void invalidEmailTest() {
            String email = "invalid-email";

            assertThrows(IllegalArgumentException.class, () -> new Customer(UUID.randomUUID(), "InvalidName", email, LocalDateTime.now()));
        }
    }


    //해피케이스 테스트 - Hamcrest 테스트
    @DisplayName("여러 명의 고객 생성 테스트")
    @ParameterizedTest
    @CsvSource(value = {"merry, merry@example.com", "jay, jay@example.com", "hanjo, hanjo@example.com"})
    void createMultiCustomerTest(String name, String email) {
        UUID customerId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();

        Customer customer = new Customer(customerId, name, email, createdAt);

        assertThat(customer.getCustomerId(), is(equalTo(customerId)));
        assertThat(customer.getName(), is(equalTo(name)));
        assertThat(customer.getEmail(), is(equalTo(email)));
        assertThat(customer.getCreateAt(), is(equalTo(createdAt)));
    }
}