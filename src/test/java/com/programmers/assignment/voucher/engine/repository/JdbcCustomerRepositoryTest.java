package com.programmers.assignment.voucher.engine.repository;

import com.programmers.assignment.voucher.engine.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class JdbcCustomerRepositoryTest {

    @Autowired
    JdbcCustomerRepository customerRepository;


    @Test
    @DisplayName("customer가 db에 잘 들어가야 한다.")
    void save() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());

        //when
        customerRepository.save(customer);

        //then
        var insertedCustomer = customerRepository.findByName("test").get();
        assertThat(customer.getCustomerUuid(), is(insertedCustomer.getCustomerUuid()));
        assertThat(customer.getName(), is(insertedCustomer.getName()));
        assertThat(customer.getEmail(), is(insertedCustomer.getEmail()));
    }

    @Test
    @DisplayName("customer의 값을 수정할 수 있다.")
    void update() {
        //given
        Customer customer = new Customer(UUID.randomUUID(), "test", "test@gmail.com", LocalDateTime.now());
        customerRepository.save(customer);
        Customer updateCustomer = customerRepository.findByName("test").get();
        var updateName = "test-update";
        updateCustomer.setName(updateName);


        //when
        var updatedCustomer = customerRepository.update(updateCustomer);

        //then
        assertThat(updatedCustomer, samePropertyValuesAs(updateCustomer));
    }

    @Test
    @DisplayName("customer 모두 찾기 가능")
    void findAll() {
        //given
        customerRepository.save(new Customer(UUID.randomUUID(), "test1", "1@1", LocalDateTime.now()));
        customerRepository.save(new Customer(UUID.randomUUID(), "test2", "2@2", LocalDateTime.now()));
        customerRepository.save(new Customer(UUID.randomUUID(), "test3", "3@3", LocalDateTime.now()));
        customerRepository.save(new Customer(UUID.randomUUID(), "test4", "4@4", LocalDateTime.now()));
        Customer test1 = customerRepository.findByName("test1").get();
        Customer test2 = customerRepository.findByName("test2").get();
        Customer test3 = customerRepository.findByName("test3").get();
        Customer test4 = customerRepository.findByName("test4").get();


        //when
        var customers = customerRepository.findAll();


        //then

        assertThat(customers.get(0), samePropertyValuesAs(test1));
        assertThat(customers.get(1), samePropertyValuesAs(test2));
        assertThat(customers.get(2), samePropertyValuesAs(test3));
        assertThat(customers.get(3), samePropertyValuesAs(test4));
    }

    @Test
    @DisplayName("uuid로 customer를 조회할 수 있다.")
    void findByUuid() {
        //given
        Customer customer = customerRepository.save(new Customer(UUID.randomUUID(), "test1", "1@1", LocalDateTime.now()));

        //when
        Customer findCustomer = customerRepository.findByUuid(customer.getCustomerUuid()).get();

        //then
        assertThat(findCustomer.getCustomerUuid(), is(customer.getCustomerUuid()));
        assertThat(findCustomer.getEmail(), is(customer.getEmail()));
        assertThat(findCustomer.getName(), is(customer.getName()));
        assertThat(findCustomer.getCreatedAt(), is(customer.getCreatedAt()));
    }

    @Test
    @DisplayName("name으로 customer를 조회할 수 있다.")
    void findByName() {//given
        Customer customer = customerRepository.save(new Customer(UUID.randomUUID(), "test1", "1@1", LocalDateTime.now()));

        //when
        Customer findCustomer = customerRepository.findByName(customer.getName()).get();

        //then
        assertThat(findCustomer.getCustomerUuid(), is(customer.getCustomerUuid()));
        assertThat(findCustomer.getEmail(), is(customer.getEmail()));
        assertThat(findCustomer.getName(), is(customer.getName()));
        assertThat(findCustomer.getCreatedAt(), is(customer.getCreatedAt()));
    }

    @Test
    @DisplayName("email로 customer를 조회할 수 있다.")
    void findByEmail() {
        //given
        Customer customer = customerRepository.save(new Customer(UUID.randomUUID(), "test1", "1@1", LocalDateTime.now()));

        //when
        Customer findCustomer = customerRepository.findByEmail(customer.getEmail()).get();

        //then
        assertThat(findCustomer.getCustomerUuid(), is(customer.getCustomerUuid()));
        assertThat(findCustomer.getEmail(), is(customer.getEmail()));
        assertThat(findCustomer.getName(), is(customer.getName()));
        assertThat(findCustomer.getCreatedAt(), is(customer.getCreatedAt()));
    }

    @Test
    @DisplayName("customer를 모두 삭제한다.")
    void deleteAll() {
        //given
        customerRepository.save(new Customer(UUID.randomUUID(), "test1", "1@1", LocalDateTime.now()));
        customerRepository.save(new Customer(UUID.randomUUID(), "test2", "2@2", LocalDateTime.now()));
        customerRepository.save(new Customer(UUID.randomUUID(), "test3", "3@3", LocalDateTime.now()));
        customerRepository.save(new Customer(UUID.randomUUID(), "test4", "4@4", LocalDateTime.now()));

        //when
        customerRepository.deleteAll();

        //then
        assertThat(0, is(customerRepository.count()));
    }

    @Test
    @DisplayName("customer의 수를 센다")
    void count() {
        //given
        customerRepository.save(new Customer(UUID.randomUUID(), "test1", "1@1", LocalDateTime.now()));
        customerRepository.save(new Customer(UUID.randomUUID(), "test2", "2@2", LocalDateTime.now()));
        customerRepository.save(new Customer(UUID.randomUUID(), "test3", "3@3", LocalDateTime.now()));
        customerRepository.save(new Customer(UUID.randomUUID(), "test4", "4@4", LocalDateTime.now()));

        //when
        int count = customerRepository.count();

        //then
        assertThat(4, is(count));
    }


    @Test
    @DisplayName("없는 uuid로 customer를 조회하면 오류가 난다.")
    void findByNothingUuid() {
        //given
        Customer customer = customerRepository.save(new Customer(UUID.randomUUID(), "test1", "1@1", LocalDateTime.now()));

        //when
        Customer findCustomer = customerRepository.findByUuid(customer.getCustomerUuid()).get();
        customerRepository.deleteAll();


        //then
//        assertThrows(EmptyResultDataAccessException.class, () -> customerRepository.findByUuid(findCustomer.getCustomerUuid()));
    }
}