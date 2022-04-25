package org.prgrms.deukyun.voucherapp.domain.customer.persistence;

import org.junit.jupiter.api.*;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.testconfig.JdbcTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringJUnitConfig
@ContextConfiguration(classes = JdbcTestConfig.class)
class JdbcCustomerRepositoryTest {

    @Autowired NamedParameterJdbcTemplate jdbcTemplate;
    JdbcCustomerRepository jdbcCustomerRepository;
    Customer customer;

    @BeforeEach
    void setUp() {
        jdbcCustomerRepository = new JdbcCustomerRepository(jdbcTemplate);
        customer = dummyCustomer();
    }

    @Nested
    class insertTest {

        @Test
        void givenCustomer_whenCallInsert_thenIdIsSetAndReturnsInsertedCustomer() {
            //when
            Customer insertedCustomer = jdbcCustomerRepository.insert(customer);

            //then
            assertCustomer(insertedCustomer, customer);
        }
    }

    @Nested
    class findAllTest {

        @Test
        void givenTwoCustomerInsertion_whenCallFindAll_thenGivesTwoCustomers() {
            //setup
            Customer customer1 = dummyCustomer();
            Customer customer2 = dummyCustomer();
            jdbcCustomerRepository.insert(customer1);
            jdbcCustomerRepository.insert(customer2);

            //when
            List<Customer> customers = jdbcCustomerRepository.findAll();

            //assert
            assertThat(customers).extracting("id")
                    .containsExactlyInAnyOrder(customer1.getId(), customer2.getId());
        }
    }

    @Nested
    class findByIdTest {

        UUID id;

        @BeforeEach
        void setup() {
            jdbcCustomerRepository.insert(customer);
        }

        @Test
        void givenIdOfInsertedCustomer_whenCallFindById_thenReturnFoundCustomerInstance() {
            //setup
            id = customer.getId();

            //when
            Optional<Customer> foundCustomer = jdbcCustomerRepository.findById(id);

            //assert
            assertCustomer(foundCustomer.get(), customer);
        }

        @Test
        void givenInvalidId_whenCallFindById_thenReturnOptionalEmpty() {
            //setup
            id = UUID.randomUUID();

            //when
            Optional<Customer> foundCustomer = jdbcCustomerRepository.findById(id);

            //assert
            assertThat(foundCustomer).isNotPresent();
        }
    }

    @Nested
    class deleteAllTest{

        @Test
        void givenTwoInsertion_whenCallDeleteAll_thenFindAllReturnsEmptyList(){
            //setup
            jdbcCustomerRepository.insert(dummyCustomer());
            jdbcCustomerRepository.insert(dummyCustomer());

            //action
            jdbcCustomerRepository.clear();

            //assert
            assertThat(jdbcCustomerRepository.findAll()).isEmpty();
        }
    }

    private void assertCustomer(Customer actualCustomer, Customer expectedCustomer) {
        assertThat(actualCustomer).isNotNull();
        assertThat(actualCustomer.getId()).isEqualTo(expectedCustomer.getId());
        assertThat(actualCustomer.getName()).isEqualTo(expectedCustomer.getName());
        assertThat(actualCustomer.getVouchers()).hasSameElementsAs(expectedCustomer.getVouchers());
    }

    private static Customer dummyCustomer() {
        return new Customer("ndy", true, Collections.emptyList());
    }

}