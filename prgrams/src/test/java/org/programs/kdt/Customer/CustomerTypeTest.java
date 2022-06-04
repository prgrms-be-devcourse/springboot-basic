package org.programs.kdt.Customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CustomerTypeTest {

  @Test
  @DisplayName("normal문자열로 CustomerType.NORMAL을 불러올 수 있다.")
  public void findNormal() {
    String s = "normal";
    assertThat(CustomerType.find(s), is(CustomerType.NORMAL));
  }

  @Test
  @DisplayName("blacklist문자열로 CustomerType.BLACKLIST을 불러올 수 있다.")
  public void findBlackList() {
    String s = "blacklist";
    assertThat(CustomerType.find(s), is(CustomerType.BLACKLIST));
  }
}
