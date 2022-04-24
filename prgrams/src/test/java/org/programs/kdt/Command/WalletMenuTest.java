package org.programs.kdt.Command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class WalletMenuTest {

  @ParameterizedTest
  @DisplayName("문자열로 WalletMenu 타입을 가져올 수 있다.")
  @ValueSource(strings = {"0", "1", "2", "3", "4", "5", "6", "esaeas"})
  void StringToWalletMenuTest(String s) {
    assertThat(WalletMenu.find(s).getClass(), is(WalletMenu.class));
  }

  @Test
  @DisplayName("문자열 0로 Wallet 타입 RETURN를 가져올 수 있다.")
  void returnTest() {
    WalletMenu command = WalletMenu.find("0");
    assertThat(command, is(WalletMenu.RETURN));
  }

  @Test
  @DisplayName("문자열 1로 Wallet 타입 CREATE를 가져올 수 있다.")
  void createTest() {
    WalletMenu command = WalletMenu.find("1");
    assertThat(command, is(WalletMenu.CREATE));
  }

  @Test
  @DisplayName("문자열 2로 Wallet 타입 FINDALL를 가져올 수 있다.")
  void findAllTest() {
    WalletMenu command = WalletMenu.find("2");
    assertThat(command, is(WalletMenu.FIND_ALL));
  }

  @Test
  @DisplayName("문자열 3로 Wallet 타입 FINDCUSTOMER를 가져올 수 있다.")
  void findCustomerTest() {
    WalletMenu command = WalletMenu.find("3");
    assertThat(command, is(WalletMenu.FIND_CUSTOMER));
  }

  @Test
  @DisplayName("문자열 4로 Wallet 타입 FINDVOUCHER를 가져올 수 있다.")
  void findVoucherTest() {
    WalletMenu command = WalletMenu.find("4");
    assertThat(command, is(WalletMenu.FIND_VOUCHER));
  }

  @Test
  @DisplayName("문자열 5로 Wallet 타입 DELETE를 가져올 수 있다.")
  void deleteTest() {
    WalletMenu command = WalletMenu.find("5");
    assertThat(command, is(WalletMenu.DELETE));
  }


  @ParameterizedTest
  @DisplayName("잘못된 문자열들로 WalletMenu Error 타입을 가져올 수 있다.")
  @ValueSource(strings = {"exit1", "create2", "list3", "blacklist4", "error5"})
  void StringToCustomerMenuErrorTest(String s) {
    assertThat(WalletMenu.find(s), is(WalletMenu.ERROR));
  }
}
