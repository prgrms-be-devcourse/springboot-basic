package org.programs.kdt.Command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CommandMenuTest {

  @ParameterizedTest
  @DisplayName("문자열로 CommandMenu 타입을 가져올 수 있다.")
  @ValueSource(strings = {"0", "1", "2", "3", "esaeas"})
  void StringToCommandTest(String s) {
    assertThat(CommandMenu.findCommand(s).getClass(), is(CommandMenu.class));
  }

  @Test
  @DisplayName("문자열 0로 CommandMenu타입 exit를 가져올 수 있다.")
  void exitTest() {
    CommandMenu command = CommandMenu.findCommand("0");
    assertThat(command, is(CommandMenu.EXIT));
  }

  @Test
  @DisplayName("문자열 1로 CommandMenu타입 voucher를 가져올 수 있다.")
  void voucherTest() {
    CommandMenu command = CommandMenu.findCommand("1");
    assertThat(command, is(CommandMenu.VOUCHER));
  }

  @Test
  @DisplayName("문자열 2로 CommandMenu타입 customer를 가져올 수 있다.")
  void customerTest() {
    CommandMenu command = CommandMenu.findCommand("2");
    assertThat(command, is(CommandMenu.CUSTOMER));
  }

  @Test
  @DisplayName("문자열 3로 CommandMenu타입 wallet를 가져올 수 있다.")
  void WalletTest() {
    CommandMenu command = CommandMenu.findCommand("3");
    assertThat(command, is(CommandMenu.WALLET));
  }

  @ParameterizedTest
  @DisplayName("잘못된 문자열들로 Command Error 타입을 가져올 수 있다.")
  @ValueSource(strings = {"exit1", "create2", "list3", "blacklist4", "error5"})
  void StringToCommandErrorTest(String s) {
    assertThat(CommandMenu.findCommand(s), is(CommandMenu.ERROR));
  }
}
