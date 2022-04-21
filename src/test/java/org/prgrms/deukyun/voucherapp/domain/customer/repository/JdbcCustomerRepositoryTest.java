package org.prgrms.deukyun.voucherapp.domain.customer.repository;

import org.junit.jupiter.api.*;
import org.prgrms.deukyun.voucherapp.domain.customer.entity.Customer;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JdbcCustomerRepositoryTest {

    static JdbcCustomerRepository jdbcCustomerRepository;
    static Customer customer;

    static DataSource dataSource;

    @BeforeAll
    static void setup() {
        dataSource = DataSourceBuilder
                .create()
                .url("jdbc:h2:mem:testdb;INIT=RUNSCRIPT FROM 'classpath:/schema.sql';DB_CLOSE_DELAY=-1")
                .driverClassName("org.h2.Driver")
                .build();
        jdbcCustomerRepository = new JdbcCustomerRepository(new NamedParameterJdbcTemplate(dataSource));
        customer = dummyCustomer();
    }


    @Nested
    class insertTest {

        @Test
        void givenCustomer_whenCallInsert_thenIdIsSetAndReturnsInsertedCustomer() {
            //when
            Customer insertedCustomer = jdbcCustomerRepository.insert(customer);

            //assert
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

    private void assertCustomer(Customer actualCustomer, Customer expectedCustomer) {
        assertThat(actualCustomer).isNotNull();
        assertThat(actualCustomer.getId()).isEqualTo(expectedCustomer.getId());
        assertThat(actualCustomer.getName()).isEqualTo(expectedCustomer.getName());
        assertThat(actualCustomer.getVouchers()).hasSameElementsAs(expectedCustomer.getVouchers());
    }

    @Nested
    class clearTest{

        @Test
        void givenTwoInsertion_whenCallClear_thenFindAllReturnsEmptyList(){
            //setup
            jdbcCustomerRepository.insert(dummyCustomer());
            jdbcCustomerRepository.insert(dummyCustomer());

            //action
            jdbcCustomerRepository.clear();

            //assert
            assertThat(jdbcCustomerRepository.findAll()).isEmpty();
        }
    }

    private static Customer dummyCustomer() {
        return new Customer("ndy", true, Collections.emptyList());
    }


    @AfterEach
    void cleanup() {
        jdbcCustomerRepository.clear();
    }
}