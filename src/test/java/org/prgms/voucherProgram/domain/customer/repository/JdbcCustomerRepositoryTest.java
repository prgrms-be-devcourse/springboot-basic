package org.prgms.voucherProgram.domain.customer.repository;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.config.Charset.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.prgms.voucherProgram.domain.customer.domain.Customer;
import org.prgms.voucherProgram.domain.voucher.domain.FixedAmountVoucher;
import org.prgms.voucherProgram.domain.voucher.domain.Voucher;
import org.prgms.voucherProgram.domain.voucher.repository.JdbcVoucherRepository;
import org.prgms.voucherProgram.domain.voucher.repository.VoucherRepository;
import org.prgms.voucherProgram.global.error.exception.NothingChangeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringJUnitConfig
class JdbcCustomerRepositoryTest {

    private static EmbeddedMysql embeddedMysql;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @BeforeAll
    static void setup() {
        MysqldConfig config = aMysqldConfig(v8_0_17)
            .withCharset(UTF8)
            .withPort(2215)
            .withUser("test", "test1234")
            .withTimeZone("Aisa/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-order_mgmt", ScriptResolver.classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    static void tearDown() {
        embeddedMysql.stop();
    }

    @AfterEach
    void clear() {
        customerRepository.deleteAll();
    }

    private Customer customer() {
        return new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
    }

    private List<Customer> customers() {
        return List.of(
            new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now()),
            new Customer(UUID.randomUUID(), "spancer", "spancer@gmail.com", LocalDateTime.now()));
    }

    @Configuration
    @ComponentScan(basePackages = {"org.prgms.voucherProgram.domain.voucher.repository",
        "org.prgms.voucherProgram.domain.customer.repository"},
        includeFilters = @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            value = {JdbcCustomerRepository.class, JdbcVoucherRepository.class}), useDefaultFilters = false)
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                .username("test")
                .password("test1234")
                .type(HikariDataSource.class)
                .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Nested
    @DisplayName("save 메서드는")
    class Describe_save {

        @Nested
        @DisplayName("고객이 주어지면")
        class Context_with_customer {
            final Customer customer = customer();

            @Test
            @DisplayName("주어진 고객을 저장하고 저장된 고객을 리턴한다.")
            void it_saves_customer_and_returns_saved_customer() {
                Customer savedCustomer = customerRepository.save(customer);

                assertThat(savedCustomer).isEqualTo(customer);
                assertThat(customerRepository.findByEmail(customer.getEmail())).isNotEmpty()
                    .get()
                    .usingRecursiveComparison()
                    .isEqualTo(customer);
            }
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("저장된 고객들이 있다면")
        class Context_with_customers {
            final List<Customer> customers = customers();

            @BeforeEach
            void prepare() {
                customers.forEach(customer -> customerRepository.save(customer));
            }

            @Test
            @DisplayName("모든 고객들을 리턴한다.")
            void it_returns_all_customer() {
                List<Customer> findCustomer = customerRepository.findAll();

                assertThat(findCustomer).hasSize(2)
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields()
                    .containsAll(customers);
            }
        }
    }

    @Nested
    @DisplayName("findByEmail 메서드는")
    class Describe_findByEmail {
        final Customer customer = customer();

        @BeforeEach
        void prepare() {
            customerRepository.save(customer);
        }

        @Nested
        @DisplayName("저장된 고객의 이메일이라면")
        class Context_with_saved_customer_email {
            @Test
            @DisplayName("이메일로 고객을 찾고 리턴한다.")
            void it_find_customer_by_email_and_returns_customer() {
                Optional<Customer> findCustomer = customerRepository.findByEmail(customer.getEmail());

                assertThat(findCustomer).isNotEmpty()
                    .get()
                    .usingRecursiveComparison()
                    .isEqualTo(customer);
            }
        }

        @Nested
        @DisplayName("저장되지 않은 고객의 이메일이라면")
        class Context_with_not_saved_customer_email {
            final String notSaveEmail = "asdsad@gmail.com";

            @Test
            @DisplayName("Optional Empty 를 리턴한다.")
            void it_returns_Optional_empty() {
                Optional<Customer> findCustomer = customerRepository.findByEmail(notSaveEmail);

                assertThat(findCustomer).isEmpty();
            }
        }

    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_findById {

        @Nested
        @DisplayName("만약 저장된 고객의 ID라면")
        class Context_with_saved_customer_id {
            final Customer customer = customer();

            @BeforeEach
            void prepare() {
                customerRepository.save(customer);
            }

            @Test
            @DisplayName("고객ID로 찾은 뒤 해당 고객을 리턴한다.")
            void it_find_customer_and_returns_customer() {
                Optional<Customer> findCustomer = customerRepository.findById(customer.getCustomerId());

                assertThat(findCustomer).isNotEmpty()
                    .get()
                    .usingRecursiveComparison()
                    .isEqualTo(customer);
            }
        }

        @Nested
        @DisplayName("만약 저장되지 않은 고객의 ID라면")
        class Context_with_not_saved_customer_id {
            final UUID notSavedId = UUID.randomUUID();

            @Test
            @DisplayName("Optional Empty 를 리턴한다.")
            void it_return_optional_empty() {
                Optional<Customer> findCustomer = customerRepository.findById(notSavedId);

                assertThat(findCustomer).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("findByVoucherId 메서드는")
    class Describe_findByVoucherId {

        @Nested
        @DisplayName("고객이 해당 바우처를 가지고 있다면")
        class Context_with_customer_has_voucher {
            final Customer customer = customer();
            final Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), customer.getCustomerId(), 100L,
                LocalDateTime.now());

            @BeforeEach
            void prepare() {
                customerRepository.save(customer);
                voucherRepository.save(voucher);
            }

            @Test
            @DisplayName("바우처ID로 고객을 찾은 뒤 해당 고객을 리턴한다.")
            void it_find_customer_and_returns_customer() {
                Optional<Customer> findCustomer = customerRepository.findByVoucherId(voucher.getVoucherId());

                assertThat(findCustomer).isNotEmpty()
                    .get()
                    .usingRecursiveComparison()
                    .isEqualTo(customer);
            }
        }

        @Nested
        @DisplayName("해당 바우처를 가진 고객이 없다면")
        class Context_with_customer_has_not_voucher {
            final UUID notSavedId = UUID.randomUUID();

            @Test
            @DisplayName("Optional Empty 를 리턴한다.")
            void it_return_optional_empty() {
                Optional<Customer> findCustomer = customerRepository.findByVoucherId(notSavedId);

                assertThat(findCustomer).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {

        @Nested
        @DisplayName("수정할 고객이 주어지면")
        class Context_with_update_customer {
            final Customer customer = customer();
            final Customer update = new Customer(customer.getCustomerId(), "aramand", "aramand@gmail.com",
                customer.getCreatedTime());

            @BeforeEach
            void prepare() {
                customerRepository.save(customer);
            }

            @Test
            @DisplayName("고객을 수정하고 수정된 고객을 리턴한다.")
            void it_updates_customer_and_returns_updated_customer() {
                Customer updatedCustomer = customerRepository.update(update);

                assertThat(updatedCustomer).isEqualTo(update);
                assertThat(customerRepository.findById(updatedCustomer.getCustomerId())).isNotEmpty()
                    .get()
                    .usingRecursiveComparison()
                    .isEqualTo(update);
            }
        }
    }

    @Nested
    @DisplayName("deleteByEmail 메서드는")
    class Describe_deleteByEmail {

        @Nested
        @DisplayName("만약 저장된 고객의 이메일이라면")
        class Context_with_saved_customer_email {
            final Customer customer = customer();

            @BeforeEach
            void prepare() {
                customerRepository.save(customer);
            }

            @Test
            @DisplayName("해당 고객을 삭제한다.")
            void it_delete_voucher() {
                customerRepository.deleteByEmail(customer.getEmail());

                assertThat(customerRepository.findByEmail(customer.getEmail())).isEmpty();
            }
        }

        @Nested
        @DisplayName("만약 저장되지 않은 고객의 이메일이라면")
        class Context_with_not_saved_customer_email {
            final String notSavedEmail = "asdfdsaf@gmail.com";

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_Exception() {
                assertThatThrownBy(() -> customerRepository.deleteByEmail(notSavedEmail))
                    .isInstanceOf(NothingChangeException.class)
                    .hasMessage("[ERROR] 해당 요청이 정상적으로 처리되지 않았습니다.");
            }
        }
    }

    @Nested
    @DisplayName("deleteById 메서드는")
    class Describe_deleteById {

        @Nested
        @DisplayName("만약 저장된 고객의 ID라면")
        class Context_with_saved_customer_id {
            final Customer customer = customer();

            @BeforeEach
            void prepare() {
                customerRepository.save(customer);
            }

            @Test
            @DisplayName("해당 고객을 삭제한다.")
            void it_delete_customer() {
                customerRepository.deleteById(customer.getCustomerId());

                assertThat(customerRepository.findById(customer.getCustomerId())).isEmpty();
            }
        }

        @Nested
        @DisplayName("만약 저장되지 않은 고객의 ID라면")
        class Context_with_not_saved_customer_id {
            final UUID notSavedId = UUID.randomUUID();

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_Exception() {
                assertThatThrownBy(() -> customerRepository.deleteById(notSavedId))
                    .isInstanceOf(NothingChangeException.class)
                    .hasMessage("[ERROR] 해당 요청이 정상적으로 처리되지 않았습니다.");
            }
        }
    }

    @Nested
    @DisplayName("deleteAll 메서드는")
    class Describe_deleteAll {

        @Nested
        @DisplayName("저장된 고객들이 있다면")
        class Context_with_customers {
            final List<Customer> customers = customers();

            @BeforeEach
            void prepare() {
                customers.forEach(customer -> customerRepository.save(customer));
            }

            @Test
            @DisplayName("모든 삭제한다.")
            void it_delete_all_customer() {
                customerRepository.deleteAll();

                assertThat(customerRepository.findAll()).isEmpty();
            }
        }
    }
}
