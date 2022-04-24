package org.programs.kdt.Command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CustomerMenuTest {
  @ParameterizedTest
  @DisplayName("문자열로 CustomerMenu 타입을 가져올 수 있다.")
  @ValueSource(strings = {"0", "1", "2", "3", "4", "5", "esaeas"})
  void StringToCustomerMenuTest(String s) {
    assertThat(CustomerMenu.find(s).getClass(), is(CustomerMenu.class));
  }

  @Test
  @DisplayName("문자열 0로 Customer타입 RETURN를 가져올 수 있다.")
  void returnTest() {
    CustomerMenu command = CustomerMenu.find("0");
    assertThat(command, is(CustomerMenu.RETURN));
  }

  @Test
  @DisplayName("문자열 1로 Customeru타입 create를 가져올 수 있다.")
  void createTest() {
    CustomerMenu command = CustomerMenu.find("1");
    assertThat(command, is(CustomerMenu.CREATE));
  }

  @Test
  @DisplayName("문자열 2로 Customeru타입 findAll를 가져올 수 있다.")
  void findAllTest() {
    CustomerMenu command = CustomerMenu.find("2");
    assertThat(command, is(CustomerMenu.FIND_ALL));
  }

  @Test
  @DisplayName("문자열 3로 Customeru타입 findEmail를 가져올 수 있다.")
  void findEmailTest() {
    CustomerMenu command = CustomerMenu.find("3");
    assertThat(command, is(CustomerMenu.FIND_EMAIL));
  }

  @Test
  @DisplayName("문자열 4로 Customeru타입 UPDATE를 가져올 수 있다.")
  void updateTest() {
    CustomerMenu command = CustomerMenu.find("4");
    assertThat(command, is(CustomerMenu.UPDATE));
  }

  @Test
  @DisplayName("문자열 5로 Customeru타입 DELETE를 가져올 수 있다.")
  void deleteTest() {
    CustomerMenu command = CustomerMenu.find("5");
    assertThat(command, is(CustomerMenu.DELETE));
  }

  @Test
  @DisplayName("문자열 6로 Customeru타입 BLACKLIST를 가져올 수 있다.")
  void blacklistTest() {
    CustomerMenu command = CustomerMenu.find("6");
    assertThat(command, is(CustomerMenu.BLACKLIST));
  }

  @Test
  @DisplayName("문자열 7로 Customeru타입 findBlacklist를 가져올 수 있다.")
  void findBlacklistTest() {
    CustomerMenu command = CustomerMenu.find("7");
    assertThat(command, is(CustomerMenu.FIND_BLACKLIST));
  }

  @ParameterizedTest
  @DisplayName("잘못된 문자열들로 CustomerMenu Error 타입을 가져올 수 있다.")
  @ValueSource(strings = {"exit1", "create2", "list3", "blacklist4", "error5"})
  void StringToCustomerMenuErrorTest(String s) {
    assertThat(CustomerMenu.find(s), is(CustomerMenu.ERROR));
  }
}
