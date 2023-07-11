package com.dev.voucherproject.customer;

import com.dev.voucherproject.model.customer.Customer;
import com.dev.voucherproject.model.storage.customer.CustomerDao;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@SpringBootTest
class CustomerDaoTest {

    @Autowired
    CustomerDao customerDao;

    @BeforeEach
    void init() {
        customerDao.deleteAll();
    }

    @Test
    @DisplayName("고객을 저장할 수 있다.")
    void insertCustomer() {
        //GIVEN
        Customer customerA = new Customer(UUID.randomUUID(), "test1", "test1@email.com", LocalDateTime.now());
        Customer customerB = new Customer(UUID.randomUUID(), "test2", "test2@email.com", LocalDateTime.now());

        //WHEN
        customerDao.insert(customerA);
        customerDao.insert(customerB);

        //THEN
        List<Customer> customers = customerDao.findAll();
        assertThat(customers.size(), is(2));
    }

    @Test
    @DisplayName("이미 등록된 이메일로 등록할 경우 고객이 저장되지 않는다.")
    void insertDuplicateEmailCustomer() {
        //GIVEN
        Customer customerA = new Customer(UUID.randomUUID(), "test1", "test1@email.com", LocalDateTime.now());
        customerDao.insert(customerA);

        //WHEN
        Customer newCustomer = new Customer(UUID.randomUUID(), "test3", customerA.getEmail(), LocalDateTime.now());
        customerDao.insert(newCustomer);

        //THEN
        List<Customer> customers = customerDao.findAll();
        assertThat(customers.size(), is(1));
    }

    @Test
    @DisplayName("이메일을 변경할 수 있다.")
    void update() {
        //GIVEN
        Customer customerA = new Customer(UUID.randomUUID(), "test1", "test1@email.com", LocalDateTime.now());
        customerDao.insert(customerA);

        //WHEN
        String newEmail = "TEST1@email.com";
        customerDao.update(customerA.updateEmail(newEmail));

        //THEN
        Customer updatedCustomerA = customerDao.findById(customerA.getCustomerId()).get();
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(updatedCustomerA.getCustomerId()).isEqualTo(customerA.getCustomerId());
            soft.assertThat(updatedCustomerA.getEmail()).isEqualTo(newEmail);
        });
    }

    @Test
    @DisplayName("이미 등록된 이메일로 변경할 경우 이메일이 변경되지 않는다.")
    void updateWithRegisteredEmail() {
        //GIVEN
        Customer customerA = new Customer(UUID.randomUUID(), "test1", "test1@email.com", LocalDateTime.now());
        Customer customerB = new Customer(UUID.randomUUID(), "test2", "test2@email.com", LocalDateTime.now());
        customerDao.insert(customerA);
        customerDao.insert(customerB);

        //WHEN
        String newEmail = customerB.getEmail();
        customerDao.update(customerA.updateEmail(newEmail));

        //THEN
        Customer updatedCustomerA = customerDao.findById(customerA.getCustomerId()).get();
        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(updatedCustomerA.getCustomerId()).isEqualTo(customerA.getCustomerId());
            soft.assertThat(updatedCustomerA.getEmail()).isNotEqualTo(newEmail);
            soft.assertThat(updatedCustomerA.getEmail()).isEqualTo(customerA.getEmail());
        });
    }

    @Test
    @DisplayName("특정 이름의 고객을 조회할 수 있다")
    void findByName() {
        //GIVEN
        Customer customerA = new Customer(UUID.randomUUID(), "test1", "test1@email.com", LocalDateTime.now());
        customerDao.insert(customerA);

        //WHEN
        Customer findCustomer = customerDao.findByName(customerA.getName()).get();

        //THEN
        assertThat(findCustomer.getCustomerId(), is(customerA.getCustomerId()));
    }

    @Test
    @DisplayName("특정 이메일의 고객을 조회할 수 있다")
    void findByEmail() {
        //GIVEN
        Customer customerA = new Customer(UUID.randomUUID(), "test1", "test1@email.com", LocalDateTime.now());
        customerDao.insert(customerA);

        //WHEN
        Customer findCustomer = customerDao.findByEmail(customerA.getEmail()).get();

        //THEN
        assertThat(findCustomer.getCustomerId(), is(customerA.getCustomerId()));
    }

    @Test
    @DisplayName("특정 ID의 고객을 제거할 수 있다.")
    void deleteById() {
        //GIVEN
        Customer customerA = new Customer(UUID.randomUUID(), "test1", "test1@email.com", LocalDateTime.now());
        customerDao.insert(customerA);

        //WHEN
        customerDao.deleteById(customerA.getCustomerId());

        //THEN
        List<Customer> customers = customerDao.findAll();
        assertThat(customers.size(), is(0));
    }
}
