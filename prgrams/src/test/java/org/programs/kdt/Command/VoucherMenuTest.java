package org.programs.kdt.Command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class VoucherMenuTest {
  @ParameterizedTest
  @DisplayName("문자열로 VoucherMenu 타입을 가져올 수 있다.")
  @ValueSource(strings = {"0", "1", "2", "3", "4", "5", "esaeas"})
  void StringToVoucherMenuTest(String s) {
    assertThat(VoucherMenu.find(s).getClass(), is(VoucherMenu.class));
  }

  @Test
  @DisplayName("문자열 0로 VoucherMenu 타입 RETURN를 가져올 수 있다.")
  void returnTest() {
    VoucherMenu vocherMenu = VoucherMenu.find("0");
    assertThat(vocherMenu, is(VoucherMenu.RETURN));
  }

  @Test
  @DisplayName("문자열 1로 VoucherMenu 타입 CREATE를 가져올 수 있다.")
  void createTest() {
    VoucherMenu vocherMenu = VoucherMenu.find("1");
    assertThat(vocherMenu, is(VoucherMenu.CREATE));
  }

  @Test
  @DisplayName("문자열 2로 VoucherMenu 타입 FINDALL를 가져올 수 있다.")
  void findAllTest() {
    VoucherMenu vocherMenu = VoucherMenu.find("2");
    assertThat(vocherMenu, is(VoucherMenu.FIND_ALL));
  }

  @Test
  @DisplayName("문자열 3로 VoucherMenu 타입 FINDPERCENT를 가져올 수 있다.")
  void findPercentTest() {
    VoucherMenu vocherMenu = VoucherMenu.find("3");
    assertThat(vocherMenu, is(VoucherMenu.FIND_PERCENT));
  }

  @Test
  @DisplayName("문자열 4로 VoucherMenu 타입 FINDFIX를 가져올 수 있다.")
  void findFixTest() {
    VoucherMenu vocherMenu = VoucherMenu.find("4");
    assertThat(vocherMenu, is(VoucherMenu.FIND_FIX));
  }

  @Test
  @DisplayName("문자열 5로 VoucherMenu 타입 DELETE를 가져올 수 있다.")
  void deleteTest() {
    VoucherMenu vocherMenu = VoucherMenu.find("5");
    assertThat(vocherMenu, is(VoucherMenu.DELETE));
  }

  @ParameterizedTest
  @DisplayName("잘못된 문자열들로 VoucherMen Error 타입을 가져올 수 있다.")
  @ValueSource(strings = {"exit1", "create2", "list3", "blacklist4", "error5"})
  void StringToVoucherMenuErrorTest(String s) {
    assertThat(VoucherMenu.find(s), is(VoucherMenu.ERROR));
  }
}
