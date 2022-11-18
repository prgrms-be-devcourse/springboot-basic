package org.prgrms.memory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import customer.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestInstance(Lifecycle.PER_CLASS)
class CustomerDBMemoryTest extends JdbcBase{

  @Autowired
  CustomerDBMemory customerMemory;

  @BeforeEach
  void clean() {
    customerMemory.deleteAll();
  }


  @DisplayName("고객 정보를 저장후 저장한 정보를 반환한다")
  @Test
  void test1() {
    //given
    Customer customer = new Customer(UUID.randomUUID(), "예오닝");
    //when
    Customer saved = customerMemory.save(customer);
    //then
    assertEquals(customer, saved);
  }

  @DisplayName("이미 있는 id 값을 저장할 때 DuplicateKeyException을 던진다")
  @Test
  void test1_1() {
    //given
    Customer customer = new Customer(UUID.randomUUID(), "예오닝");
    customerMemory.save(customer);
    //when&then
    assertThrows(DuplicateKeyException.class, () -> customerMemory.save(customer));
  }

  @DisplayName("특정 id로 고객 정보를 찾아 반환한다")
  @Test
  void test2() {
    //given
    Customer customer = new Customer(UUID.randomUUID(), "예오닝2");
    customerMemory.save(customer);
    //when
    Optional<Customer> foundCustomer = customerMemory.findById(customer.getId());
    //then
    assertThat(customer).usingRecursiveComparison().isEqualTo(foundCustomer.get());
  }

  @DisplayName("고객 id가 존재하지 않을 경우 Optional.empty를 반환한다")
  @Test
  void test2_1() {
    //given
    Optional<Customer> foundCustomer = customerMemory.findById(UUID.randomUUID());
    //when&then
    assertEquals(Optional.empty(), foundCustomer);
  }

  @DisplayName("고객테이블의 모든 정보를 반환한다")
  @Test
  void test3() {
    //given
    Customer customer1 = new Customer(UUID.randomUUID(), "예오닝");
    Customer customer2 = new Customer(UUID.randomUUID(), "예오닝2");
    customerMemory.save(customer1);
    customerMemory.save(customer2);
    //when
    List<Customer> all = customerMemory.findAll();
    //then
    assertEquals(2, all.size());
  }

  @DisplayName("저장된 고객 정보가 없을 경우 빈 배열을 반환한다")
  @Test
  void test3_1() {
    //given
    customerMemory.deleteAll();
    //when
    List<Customer> all = customerMemory.findAll();
    //then
    assertTrue(all.isEmpty());
  }

  @DisplayName("고객 테이블에 저장된 모든 정보를 지운다")
  @Test
  void test4() {
    //given
    customerMemory.save(new Customer(UUID.randomUUID(), "예온1"));
    customerMemory.save(new Customer(UUID.randomUUID(), "예온2"));
    //when
    customerMemory.deleteAll();
    List<Customer> all = customerMemory.findAll();
    //then
    assertTrue(all.isEmpty());
  }

  @DisplayName("특정 id를 가진 고객의 정보를 삭제한다")
  @Test
  void test5() {
    //given
    Customer customer = new Customer(UUID.randomUUID(), "예오닝3");
    customerMemory.save(customer);
    //when
    Optional<Customer> deleted = customerMemory.deleteById(customer.getId());
    //then
    assertThat(customer).usingRecursiveComparison().isEqualTo(deleted.get());
  }

  @DisplayName("지울 id 가 존재하지 않으면 Optional.empty()를 반환한다")
  @Test
  void test5_1() {
    //when
    Optional<Customer> deleted = customerMemory.deleteById(UUID.randomUUID());
    //then
    assertEquals(deleted, Optional.empty());
  }

}