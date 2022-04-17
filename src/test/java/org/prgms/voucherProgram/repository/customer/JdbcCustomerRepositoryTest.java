package org.prgms.voucherProgram.repository.customer;

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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.exception.NothingChangeException;
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
    private JdbcCustomerRepository jdbcCustomerRepository;

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
        jdbcCustomerRepository.deleteAll();
    }

    @DisplayName("고객을 저장한다.")
    @Test
    void should_ReturnCustomer_When_nonDuplicateCustomer() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        // when
        jdbcCustomerRepository.save(customer);
        // then
        assertThat(jdbcCustomerRepository.findByEmail(customer.getEmail())).isNotEmpty()
            .get()
            .usingRecursiveComparison()
            .isEqualTo(customer);
    }

    @DisplayName("모든 고객을 조회한다.")
    @Test
    void should_ReturnAllCustomers() {
        // given
        List<Customer> customers = List.of(
            new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now()),
            new Customer(UUID.randomUUID(), "spancer", "spancer@gmail.com", LocalDateTime.now()));
        customers.forEach(jdbcCustomerRepository::save);

        // when
        List<Customer> findCustomer = jdbcCustomerRepository.findAll();

        // then
        assertThat(findCustomer).hasSize(2)
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields()
            .containsAll(customers);
    }

    @DisplayName("모든 고객을 삭제한다.")
    @Test
    void should_DeleteAllCustomer() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.save(customer);
        // when
        jdbcCustomerRepository.deleteAll();
        // then
        assertThat(jdbcCustomerRepository.findAll()).isEmpty();
    }

    @DisplayName("이메일을 통해 고객을 조회한다.")
    @Test
    void should_ReturnCustomer_When_EmailIsExist() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.save(customer);
        // when
        Optional<Customer> findCustomer = jdbcCustomerRepository.findByEmail(customer.getEmail());
        // then
        assertThat(findCustomer).isNotEmpty();
        assertThat(findCustomer.get()).usingRecursiveComparison()
            .isEqualTo(customer);
    }

    @DisplayName("저장되지 않은 이메일로 조회 시 empty를 리턴한다.")
    @Test
    void should_ReturnEmpty_When_EmailIsNotExist() {
        //given
        String email = "asd@gmail.com";
        //when
        Optional<Customer> findCustomer = jdbcCustomerRepository.findByEmail(email);
        //then
        assertThat(findCustomer).isEmpty();
    }

    @DisplayName("고객 정보를 수정한다.")
    @Test
    void should_updateCustomer_When_nonDuplicateCustomer() {
        // given
        UUID customerId = UUID.randomUUID();
        LocalDateTime createdTime = LocalDateTime.now();
        Customer customer = new Customer(customerId, "hwan", "hwan@gmail.com", createdTime);
        jdbcCustomerRepository.save(customer);
        // when
        customer = new Customer(customerId, "spancer", "spancer@gmail.com", createdTime, LocalDateTime.now());
        jdbcCustomerRepository.update(customer);
        // then
        Optional<Customer> updateCustomer = jdbcCustomerRepository.findByEmail(customer.getEmail());
        assertThat(updateCustomer).isNotEmpty()
            .get()
            .usingRecursiveComparison()
            .isEqualTo(customer);
    }

    @DisplayName("ID를 통해 고객을 조회한다.")
    @Test
    void should_ReturnCustomer_When_IdIsExist() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.save(customer);
        // when
        Optional<Customer> findCustomer = jdbcCustomerRepository.findById(customer.getCustomerId());
        // then
        assertThat(findCustomer).isNotEmpty();
        assertThat(findCustomer.get()).usingRecursiveComparison()
            .isEqualTo(customer);
    }

    @DisplayName("저장되지 않은 ID로 조회 시 empty를 리턴한다.")
    @Test
    void should_ReturnEmpty_When_IdIsNotExist() {
        // given
        UUID id = UUID.randomUUID();
        // when
        Optional<Customer> findCustomer = jdbcCustomerRepository.findById(id);
        // then
        assertThat(findCustomer).isEmpty();
    }

    @DisplayName("ID를 통해 고객을 삭제한다.")
    @Test
    void should_DeleteCustomer_When_IdIsExist() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.save(customer);
        // when
        jdbcCustomerRepository.deleteById(customer.getCustomerId());
        // then
        assertThat(jdbcCustomerRepository.findByEmail(customer.getEmail())).isEmpty();
    }

    @DisplayName("잘못된 ID로 삭제하려고 하면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_IdIsNotExist() {
        // given
        UUID id = UUID.randomUUID();
        // when
        // then
        assertThatThrownBy(() -> jdbcCustomerRepository.deleteById(id))
            .isInstanceOf(NothingChangeException.class)
            .hasMessage("[ERROR] 해당 요청이 정상적으로 처리되지 않았습니다.");
    }

    @DisplayName("이메일을 통해 고객을 삭제한다.")
    @Test
    void should_DeleteCustomer_When_EmailIsExist() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.save(customer);
        // when
        jdbcCustomerRepository.deleteByEmail(customer.getEmail());
        // then
        assertThat(jdbcCustomerRepository.findByEmail(customer.getEmail())).isEmpty();
    }

    @DisplayName("잘못된 ID로 삭제하려고 하면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_EmailIsNotExist() {
        // given
        String email = "spancer@gmail.com";
        // when
        // then
        assertThatThrownBy(() -> jdbcCustomerRepository.deleteByEmail(email))
            .isInstanceOf(NothingChangeException.class)
            .hasMessage("[ERROR] 해당 요청이 정상적으로 처리되지 않았습니다.");
    }

    @Configuration
    @ComponentScan(basePackages = "org.prgms.voucherProgram.repository.customer",
        excludeFilters = @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            value = BlackListRepository.class))
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
}
