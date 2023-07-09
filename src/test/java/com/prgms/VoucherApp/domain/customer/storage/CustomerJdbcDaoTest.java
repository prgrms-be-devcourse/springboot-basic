package com.prgms.VoucherApp.domain.customer.storage;

import com.prgms.VoucherApp.domain.customer.dto.CustomerUpdateReqDto;
import com.prgms.VoucherApp.domain.customer.model.Customer;
import com.prgms.VoucherApp.domain.customer.model.CustomerDao;
import com.prgms.VoucherApp.domain.customer.model.CustomerJdbcDao;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;

@JdbcTest
@Import(CustomerJdbcDao.class)
class CustomerJdbcDaoTest {

    @Autowired
    CustomerDao customerDao;

    @Test
    @DisplayName("고객를 저장한다.")
    void saveCustomer() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);

        // when
        customerDao.save(customer);
        Optional<Customer> findCustomer = customerDao.findById(customer.getCustomerId());

        // then
        MatcherAssert.assertThat(findCustomer.isEmpty(), is(false));
        MatcherAssert.assertThat(findCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("전체 고객을 조회한다.")
    void findCustomers() {
        // given
        Customer customerA = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);
        Customer customerB = new Customer(UUID.randomUUID(), CustomerStatus.BLACKLIST);

        // when
        customerDao.save(customerA);
        customerDao.save(customerB);

        List<Customer> findCustomers = customerDao.findAll();

        // then
        MatcherAssert.assertThat(findCustomers.isEmpty(), is(false));
        MatcherAssert.assertThat(findCustomers, contains(samePropertyValuesAs(customerA), samePropertyValuesAs(customerB)));
    }

    @Test
    @DisplayName("한 명의 고객을 아이디로 조회한다.")
    void findOneCustomer() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);

        // when
        customerDao.save(customer);

        Optional<Customer> findCustomer = customerDao.findById(customer.getCustomerId());

        // then
        MatcherAssert.assertThat(findCustomer.isEmpty(), is(false));
        MatcherAssert.assertThat(findCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("아이디로 조회를 실패")
    void findOneCustomerFail() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);

        // when
        customerDao.save(customer);
        Optional<Customer> findCustomer = customerDao.findById(UUID.randomUUID());

        // then
        MatcherAssert.assertThat(findCustomer.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객을 고객 상태로 조회한다.")
    void findCustomersByStatus() {
        // given
        Customer customerA = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);
        Customer customerB = new Customer(UUID.randomUUID(), CustomerStatus.BLACKLIST);
        Customer customerC = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);

        // when
        customerDao.save(customerA);
        customerDao.save(customerB);
        customerDao.save(customerC);

        List<Customer> normalCustomers = customerDao.findByCustomerStatus(CustomerStatus.NORMAL);
        List<Customer> blacklistCustomers = customerDao.findByCustomerStatus(CustomerStatus.BLACKLIST);

        // then
        MatcherAssert.assertThat(normalCustomers, Matchers.hasSize(2));
        MatcherAssert.assertThat(blacklistCustomers, Matchers.hasSize(1));
    }

    @Test
    @DisplayName("고객 상태로 조회가 되지 않은 경우")
    void findCustomersByStatusFail() {
        // given
        Customer customerA = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);
        Customer customerB = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);

        // when
        customerDao.save(customerA);
        customerDao.save(customerB);

        List<Customer> blacklistCustomers = customerDao.findByCustomerStatus(CustomerStatus.BLACKLIST);

        // then
        MatcherAssert.assertThat(blacklistCustomers, Matchers.hasSize(0));
    }

    @Test
    @DisplayName("고객의 상태를 블랙 리스트로 변경한다.")
    void updateCustomerStatus() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);
        customerDao.save(customer);

        // when
        CustomerUpdateReqDto updateReqDto = new CustomerUpdateReqDto(customer.getCustomerId(), CustomerStatus.BLACKLIST);
        customerDao.updateStatus(updateReqDto);
        Optional<Customer> findCustomer = customerDao.findById(customer.getCustomerId());

        // then
        MatcherAssert.assertThat(findCustomer.get().getCustomerStatus(), is(CustomerStatus.BLACKLIST));
    }

    @Test
    @DisplayName("고객을 삭제한다.")
    void deleteCustomer() {
        // given
        Customer customerA = new Customer(UUID.randomUUID(), CustomerStatus.NORMAL);
        Customer customerB = new Customer(UUID.randomUUID(), CustomerStatus.BLACKLIST);

        // when
        customerDao.save(customerA);
        customerDao.save(customerB);

        customerDao.deleteById(customerA.getCustomerId());
        List<Customer> findCustomers = customerDao.findAll();

        // then
        MatcherAssert.assertThat(findCustomers, hasSize(1));
        MatcherAssert.assertThat(findCustomers, hasItem(not(samePropertyValuesAs(customerA))));
        MatcherAssert.assertThat(findCustomers, hasItem(samePropertyValuesAs(customerB)));
    }
}