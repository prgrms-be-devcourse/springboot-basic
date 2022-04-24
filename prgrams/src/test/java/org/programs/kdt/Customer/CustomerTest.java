package org.programs.kdt.Customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programs.kdt.Exception.InvalidValueException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerTest {

  @Test
  @DisplayName("유저를 생성할 수 있다.")
  void createUserTest() {
    UUID uuid = UUID.randomUUID();
    String name = "choi";
    String email = "choi@naver.com";
    LocalDateTime lastLoginAt = LocalDateTime.now();
    LocalDateTime createAt = LocalDateTime.now();
    Customer customer = new Customer(uuid, name, email, createAt);
    assertThat(customer.getCustomerId(), is(uuid));
    assertThat(customer.getName(), is(name));
    assertThat(customer.getEmail(), is(email));
    assertThat(customer.getCreatedAt(), is(createAt));
    assertThat(customer.getCustomerType(), is(CustomerType.NORMAL));
  }

  @Test
  @DisplayName("이름이 없는 유저는 생성할 수 없다.")
  void createFailNameTest() {
    UUID uuid = UUID.randomUUID();
    String name = "";
    String email = "choi@naver.com";
    LocalDateTime lastLoginAt = LocalDateTime.now();
    LocalDateTime createAt = LocalDateTime.now();
    assertThrows(InvalidValueException.class, () -> new Customer(uuid, name, email, createAt));
  }

  @Test
  @DisplayName("이름을 변경할 수 있다")
  void testUpdateName() {
    UUID uuid = UUID.randomUUID();
    String name = "choi";
    String email = "choi@naver.com";
    LocalDateTime lastLoginAt = LocalDateTime.now();
    LocalDateTime createAt = LocalDateTime.now();
    Customer customer = new Customer(uuid, name, email, createAt);
    customer.changeName("woong");
    assertThat(customer.getName(), is("woong"));
  }

  @Test
  @DisplayName("공백이름으로 이름을 변경할 수 없다.")
  void testBlankName() {
    UUID uuid = UUID.randomUUID();
    String name = "choi";
    String email = "choi@naver.com";
    LocalDateTime lastLoginAt = LocalDateTime.now();
    LocalDateTime createAt = LocalDateTime.now();
    Customer customer = new Customer(uuid, name, email, createAt);
    assertThrows(InvalidValueException.class, () -> customer.changeName(""));
  }

  @Test
  @DisplayName("유저를 블랙리스트로 등록할 수 있다.")
  void testSetBlacklist() {
    UUID uuid = UUID.randomUUID();
    String name = "choi";
    String email = "choi@naver.com";
    LocalDateTime lastLoginAt = LocalDateTime.now();
    LocalDateTime createAt = LocalDateTime.now();
    Customer customer = new Customer(uuid, name, email, createAt);
    customer.setBlacklist();
    assertThat(customer.getCustomerType(), is(CustomerType.BLACKLIST));
  }
}
