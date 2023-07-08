package com.prgrms.commandLineApplication.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CustomerTest {

  @ParameterizedTest
  @CsvSource(value = {"qkrdmswl1018@naver.com|박은지", "test@gmail.com|사용자2", "email@naver.com|사용자2"}, delimiter = '|')
  @DisplayName("올바른 형식의 이메일과 이름을 작성했을 경우 고객이 생성된다.")
  void createCustomer_Success(String email, String name) {
    Customer createdCustomer = Customer.of(UUID.randomUUID(), email, name);
    assertThat(createdCustomer).isNotNull();
  }

  @ParameterizedTest
  @CsvSource(value = {"qkrdmswl1018naver.com|박은지", "test@gma@$il.com|사용자1", "em!$ail@navercom|사용자2"}, delimiter = '|')
  @DisplayName("이메일 형식이 맞지 않을 경우 예외가 발생한다.")
  void emailFormat_ErrorSuccess(String email, String name) {
    Assertions.assertThatThrownBy(() -> Customer.of(UUID.randomUUID(), email, name))
            .isInstanceOf(IllegalArgumentException.class);

  }

}
