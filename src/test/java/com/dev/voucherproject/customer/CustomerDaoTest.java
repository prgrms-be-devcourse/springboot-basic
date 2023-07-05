package com.dev.voucherproject.customer;

import com.dev.voucherproject.model.customer.Customer;
import com.dev.voucherproject.model.storage.customer.CustomerDao;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerDaoTest {

    @Autowired
    CustomerDao customerDao;

    Customer customerA;

    Customer customerB;

    @BeforeAll
    void init() {
        customerDao.deleteAll();
        customerA = new Customer(UUID.randomUUID(), "test1", "test1@email.com", LocalDateTime.now());
        customerB = new Customer(UUID.randomUUID(), "test2", "test2@email.com", LocalDateTime.now());
    }

    @Test
    @Order(1)
    @DisplayName("고객을 저장할 수 있다.")
    void insertCustomer() {
        //WHEN
        customerDao.insert(customerA);
        customerDao.insert(customerB);

        //THEN
        List<Customer> customers = customerDao.findAll();
        assertThat(customers.size(), is(2));
    }

    @Test
    @Order(2)
    @DisplayName("이미 등록된 이메일로 등록할 경우 고객이 저장되지 않는다.")
    void insertDuplicateEmailCustomer() {
        //GIVEN
        Customer newCustomer = new Customer(UUID.randomUUID(), "test3", customerA.getEmail(), LocalDateTime.now());

        //WHEN
        customerDao.insert(newCustomer);

        //THEN
        List<Customer> customers = customerDao.findAll();
        assertThat(customers.size(), is(2));
    }

    @Test
    @Order(3)
    @DisplayName("이미 등록된 이메일로 변경할 경우 이메일이 변경되지 않는다.")
    void updateWithRegisteredEmail() {
        //GIVEN
        String newEmail = "test2@email.com";

        //WHEN
        customerDao.update(customerA.updateEmail(newEmail));

        //THEN
        Customer updatedCustomerA = customerDao.findById(customerA.getCustomerId()).get();


        assertThat(updatedCustomerA.getCustomerId(), is(customerA.getCustomerId()));
        assertThat(updatedCustomerA.getEmail(), is(customerA.getEmail()));
    }

    @Test
    @Order(4)
    @DisplayName("특정 이메일의 고객을 조회할 수 있다")
    void findByEmail() {
        //GIVEN
        String email = customerA.getEmail();

        //WHEN
        Customer findCustomerByEmail = customerDao.findByEmail(email).get();

        //THEN
        assertThat(findCustomerByEmail.getCustomerId(), is(customerA.getCustomerId()));
    }

    @Test
    @Order(5)
    @DisplayName("이메일을 변경할 수 있다.")
    void update() {
        //GIVEN
        String newEmail = "update1@email.com";

        //WHEN
        customerDao.update(customerA.updateEmail(newEmail));

        //THEN
        Customer updatedCustomerA = customerDao.findById(customerA.getCustomerId()).get();
        assertThat(updatedCustomerA.getCustomerId(), is(customerA.getCustomerId()));
        assertThat(updatedCustomerA.getEmail(), is(newEmail));
    }

    @Test
    @Order(6)
    @DisplayName("특정 ID의 고객을 제거할 수 있다.")
    void deleteById() {
        //GIVEN
        UUID customerBId = customerB.getCustomerId();

        //WHEN
        customerDao.deleteById(customerBId);

        //THEN
        List<Customer> customers = customerDao.findAll();
        assertThat(customers.size(), is(1));
    }
}
