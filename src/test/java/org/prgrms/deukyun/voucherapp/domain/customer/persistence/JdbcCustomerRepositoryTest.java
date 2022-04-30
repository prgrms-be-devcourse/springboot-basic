package org.prgrms.deukyun.voucherapp.domain.customer.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.testutil.JdbcTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.customer;

@Transactional
@SpringJUnitConfig
@ContextConfiguration(classes = JdbcTestConfig.class)
class JdbcCustomerRepositoryTest {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    JdbcCustomerRepository jdbcCustomerRepository;
    Customer customer;

    @BeforeEach
    void setUp() {
        jdbcCustomerRepository = new JdbcCustomerRepository(jdbcTemplate);
        customer = customer();
    }

    @Test
    void 성공_삽입() {
        //when
        Customer insertedCustomer = jdbcCustomerRepository.insert(customer);

        //then
        assertCustomer(insertedCustomer, customer);
    }

    @Test
    void 성공_전체조회() {
        //given
        Customer customer1 = customer();
        Customer customer2 = customer();
        jdbcCustomerRepository.insert(customer1);
        jdbcCustomerRepository.insert(customer2);

        //when
        List<Customer> customers = jdbcCustomerRepository.findAll();

        //then
        assertThat(customers).extracting("id")
                .containsExactlyInAnyOrder(customer1.getId(), customer2.getId());
    }

    @Test
    void 성공_차단_전체조회() {
        //given
        List<Customer> customers = List.of(customer(), customer(), customer(), customer(), customer());
        Object[] blockedCustomerIds = customers.stream().filter(Customer::isBlocked).map(Customer::getId).toArray();
        customers.forEach(c -> jdbcCustomerRepository.insert(c));

        //when
        List<Customer> foundBlockedCustomers = jdbcCustomerRepository.findAllBlocked();

        //then
        assertThat(foundBlockedCustomers).extracting("id")
                .containsExactlyInAnyOrder(blockedCustomerIds);
    }

    @Test
    void 성공() {
        //given
        UUID id = customer.getId();
        jdbcCustomerRepository.insert(customer);

        //when
        Optional<Customer> foundCustomer = jdbcCustomerRepository.findById(id);

        //then
        assertCustomer(foundCustomer.get(), customer);
    }

    @Test
    void 성공_아이디가_없을경우_OptionalEmpty_반환() {
        //given
        UUID id = UUID.randomUUID();
        jdbcCustomerRepository.insert(customer);

        //when
        Optional<Customer> foundCustomer = jdbcCustomerRepository.findById(id);

        //then
        assertThat(foundCustomer).isNotPresent();
    }

    @Test
    void 성공_전체_삭제() {
        //given
        jdbcCustomerRepository.insert(customer());
        jdbcCustomerRepository.insert(customer());

        //when
        jdbcCustomerRepository.clear();

        //then
        assertThat(jdbcCustomerRepository.findAll()).isEmpty();
    }

    private void assertCustomer(Customer actualCustomer, Customer expectedCustomer) {
        assertThat(actualCustomer).isNotNull();
        assertThat(actualCustomer.getId()).isEqualTo(expectedCustomer.getId());
        assertThat(actualCustomer.getName()).isEqualTo(expectedCustomer.getName());
        assertThat(actualCustomer.getVouchers()).hasSameElementsAs(expectedCustomer.getVouchers());
    }


}