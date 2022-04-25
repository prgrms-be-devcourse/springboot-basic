package org.prgrms.kdt.domain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.prgrms.kdt.dto.CustomerDto;

class CustomerTest {

  final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Nested
  @DisplayName("고객은 Email에")
  class Described_constructor {

    @Nested
    @DisplayName("null이나 빈 문자열을 전달하면")
    class Context_with_null_or_blank {

      @ParameterizedTest
      @NullAndEmptySource
      @DisplayName("예외를 발생시킨다.")
      void it_throws_exception(String email) {
        var customerDto = new CustomerDto("abc", email);
        var customer = new Customer(customerDto);

        var sut = validator.validate(customer);

        assertThat(sut.size()).isNotZero();
      }
    }

    @Nested
    @DisplayName("50 글자를 넘으면")
    class Context_with_over_max_length {

      @Test
      @DisplayName("예외를 발생시킨다.")
      void it_throws_exception() {
        String email = "thisisoverfiftywordtomakeemailclassforrule@gmail.com";
        var customerDto = new CustomerDto("abc", email);
        var customer = new Customer(customerDto);

        var sut = validator.validate(customer);

        assertThat(sut.size()).isNotZero();
      }
    }

    @Nested
    @DisplayName("잘못된 형식으로 입력하면")
    class Context_with_invalid_email {

      @ParameterizedTest
      @ValueSource(strings = {"thisisnotemail", "Abc.example.com", "A@b@example.com"})
      @DisplayName("예외를 발생시킨다.")
      void it_throws_exception(String email) {
        var customerDto = new CustomerDto("abc", email);
        var customer = new Customer(customerDto);

        var sut = validator.validate(customer);

        assertThat(sut.size()).isNotZero();
      }
    }

    @Nested
    @DisplayName("올바른 형식의 이메일을 전달하면")
    class Context_with_valid_email {

      @ParameterizedTest
      @ValueSource(strings = {"real@gmail.com", "real123@naver.re.com", "username@domain.com"})
      @DisplayName("정상적으로 생성된다.")
      void it_return_email(String email) {
        Customer customer = new Customer(new CustomerDto("abc", email));

        assertThat(customer).isNotNull();
        assertThat(customer.getEmail()).isEqualTo(email);
      }
    }
  }

  @Nested
  @DisplayName("Customer는 Name에")
  class Describe_constructor {

    @Nested
    @DisplayName("null이나 빈 값을 전달하면")
    class Context_with_null_or_blank {

      @ParameterizedTest
      @NullAndEmptySource
      @DisplayName("예외가 발생한다")
      void it_throw_exception(String name) {
        var customerDto = new CustomerDto(name, "abc@gmail.com");
        var customer = new Customer(customerDto);

        var sut = validator.validate(customer);

        assertThat(sut.size()).isNotZero();
      }
    }

    @Nested
    @DisplayName("20글자를 넘으면")
    class Context_with_over_max_length {

      @Test
      @DisplayName("예외가 발생한다")
      void it_throw_exception() {
        var customerDto = new CustomerDto("abcdefghijklmnopqrstuvwxyz", "abc@gmail.com");
        var customer = new Customer(customerDto);

        var sut = validator.validate(customer);

        assertThat(sut.size()).isNotZero();
      }
    }

    @Nested
    @DisplayName("알파벳이 아닌 문자가 포함되어 있으면")
    class Context_with_non_alphabet_characters {

      @Test
      @DisplayName("예외가 발생한다")
      void it_throw_exception() {
        var customerDto = new CustomerDto("asd31", "abc@gmail.com");
        var customer = new Customer(customerDto);

        var sut = validator.validate(customer);

        assertThat(sut.size()).isEqualTo(1);
        sut.forEach(System.out::println);
      }
    }

    @Nested
    @DisplayName("올바른 이름을 전달하면")
    class Context_with_valid_name {

      @Test
      @DisplayName("정상적으로 생성된다")
      void it_should_success() {
        Customer customer = new Customer(new CustomerDto("abcd", "abc@gmail.com"));

        assertThat(customer).isNotNull();
        assertThat(customer.getName()).isEqualTo("abcd");
      }
    }
  }
}