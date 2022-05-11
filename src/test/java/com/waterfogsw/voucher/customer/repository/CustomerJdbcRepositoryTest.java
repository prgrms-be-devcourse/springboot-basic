package com.waterfogsw.voucher.customer.repository;

import com.waterfogsw.voucher.customer.model.Customer;
import com.waterfogsw.voucher.voucher.exception.ResourceNotFoundException;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Sql(scripts = {"classpath:sql/customerTestTableInit.sql"})
@Sql(scripts = {"classpath:sql/customerTestTableTearDown.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    @Autowired
    CustomerJdbcRepository repository;

    @Autowired
    DataSource dataSource;

    @Configuration
    @ComponentScan(
            basePackages = {"com.waterfogsw.voucher.customer"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/test_voucher_mgmt")
                    .username("root")
                    .password("02709580")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }
    }

    @Order(1)
    @Nested
    @DisplayName("insert 메서드는")
    class Describe_insert {

        @Nested
        @DisplayName("customer 가 null 이면")
        class Context_with_null {

            @Test
            @DisplayName("IllegalArgumentException 에러를 던진다")
            void It_throw_IllegalArgumentException() {
                assertThrows(IllegalArgumentException.class, () -> repository.insert(null));
            }
        }

        @Nested
        @DisplayName("customer 가 null 이 아니면")
        class Context_with_not_null {

            @Test
            @DisplayName("저장한 customer를 리턴한다")
            void It_save_customer() {
                final var customer = new Customer("san");
                final var insertedCustomer = repository.insert(customer);
                assertThat(insertedCustomer.getId(), is(1L));
                assertThat(insertedCustomer.getName(), is(customer.getName()));
            }
        }

        @Nested
        @DisplayName("customer의 id값이 0이 아니고, DB에 해당 id값의 엔티티가 존재할 경우")
        class Context_with_exit_id {

            @Test
            @DisplayName("해당 엔티티를 업데이트 한다")
            void It_update() {
                final var customer1 = new Customer("san");
                repository.insert(customer1);

                final var customer2 = new Customer(1L, "hello");
                final var updatedCustomer = repository.insert(customer2);
                assertThat(updatedCustomer.getId(), is(1L));
                assertThat(updatedCustomer.getName(), is("hello"));
            }
        }

        @Nested
        @DisplayName("customer의 id값이 0이 아니고, DB에 해당 id값의 엔티티가 존재하지 않는경우")
        class Context_with_not_exit_id {

            @Test
            @DisplayName("IllegalStateException 에러가 발생한다")
            void It_IllegalStateException() {
                final var customer1 = new Customer("san");
                repository.insert(customer1);

                final var customer2 = new Customer(5L, "hello");
                assertThrows(IllegalStateException.class, () -> repository.insert(customer2));
            }
        }
    }

    @Order(2)
    @Nested
    @DisplayName("findAll메서드는")
    class Describe_findAll {

        @Nested
        @DisplayName("호출되면")
        class Context_with_call {

            @Test
            @DisplayName("저장된 모든 customer 리스트를 반환한다")
            void It_return_customer_list() {
                final var customer1 = new Customer("hi1");
                final var customer2 = new Customer("hi2");
                final var customer3 = new Customer("hi3");
                final var customer4 = new Customer("hi4");

                repository.insert(customer1);
                repository.insert(customer2);
                repository.insert(customer3);
                repository.insert(customer4);

                assertThat(repository.findAll().size(), is(4));
            }
        }
    }

    @Order(3)
    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {

        @Nested
        @DisplayName("해당 id의 엔티티가 존재하면")
        class Context_with_exist_id {

            @Test
            @DisplayName("해당 엔티티를 반환한다")
            void It_return_entity() {
                final var customer = new Customer("hello");
                repository.insert(customer);

                final var findCustomer = repository.findById(1L);
                assertThat(findCustomer.getId(), is(1L));
                assertThat(findCustomer.getName(), is(customer.getName()));
            }
        }

        @Nested
        @DisplayName("해당 id의 엔티티가 존재하지 않으면")
        class Context_with_not_exist_id {

            @Test
            @DisplayName("ResourceNotFoundException 을 발생시킨다")
            void It_throws_ResourceNotFoundException() {
                assertThrows(ResourceNotFoundException.class, () -> repository.findById(100L));
            }
        }
    }

    @Order(4)
    @Nested
    @DisplayName("deleteById 메서드는")
    class Describe_deleteById {

        @Nested
        @DisplayName("해당 id의 엔티티가 존재하면")
        class Context_with_exist_id {

            @Test
            @DisplayName("해당 엔티티를 삭제한다")
            void It_return_entity() {
                final var customer = new Customer("hello");
                repository.insert(customer);
                assertDoesNotThrow(() -> repository.findById(1L));

                repository.deleteById(1L);
                assertThrows(ResourceNotFoundException.class, () -> repository.findById(1L));
            }
        }

        @Nested
        @DisplayName("해당 id의 엔티티가 존재하지 않으면")
        class Context_with_not_exist_id {

            @Test
            @DisplayName("ResourceNotFoundException 을 발생시킨다")
            void It_throws_ResourceNotFoundException() {
                assertThrows(ResourceNotFoundException.class, () -> repository.deleteById(100L));
            }
        }
    }
}