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
import org.prgms.voucherProgram.entity.customer.Customer;
import org.prgms.voucherProgram.exception.DuplicateEmailException;
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
        assertThat(jdbcCustomerRepository.findByEmail(customer.getEmail()).get()).usingRecursiveComparison()
            .isEqualTo(customer);
    }

    @DisplayName("저장하려는 고객의 이메일이 중복된다면 예외를 발생한다.")
    @Test
    void should_ThrowException_When_DuplicateCustomerEmail() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.save(customer);
        // when
        // then
        assertThatThrownBy(() -> jdbcCustomerRepository.save(customer))
            .isInstanceOf(DuplicateEmailException.class)
            .hasMessage("[ERROR] 중복된 이메일이 존재합니다.");
    }

    @DisplayName("모든 고객을 조회한다.")
    @Test
    void should_ReturnAllCustomers() {
        // given
        Customer customerOne = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        Customer customerTwo = new Customer(UUID.randomUUID(), "pobi", "pobi@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.save(customerOne);
        jdbcCustomerRepository.save(customerTwo);
        // when
        List<Customer> findCustomer = jdbcCustomerRepository.findAll();
        // then
        assertThat(findCustomer).hasSize(2)
            .extracting("customerId", "name", "email", "createdTime")
            .contains(tuple(customerOne.getCustomerId(), customerOne.getName(), customerOne.getEmail(),
                    customerOne.getCreatedTime())
                , tuple(customerTwo.getCustomerId(), customerTwo.getName(), customerTwo.getEmail(),
                    customerTwo.getCreatedTime()));
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
        Customer customer = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.save(customer);
        // when
        customer.login();
        customer.changeName("spancer");
        customer.changeEmail("spancer@gmail.com");
        jdbcCustomerRepository.update(customer);
        // then
        Optional<Customer> updateCustomer = jdbcCustomerRepository.findByEmail(customer.getEmail());
        assertThat(updateCustomer).isNotEmpty();
        assertThat(updateCustomer.get()).usingRecursiveComparison()
            .isEqualTo(customer);
    }

    @DisplayName("수정하려는 고객의 이메일이 중복된다면 에러를 발생한다.")
    @Test
    void should_ThrowException_When_DuplicateCustomer() {
        // given
        Customer customerOne = new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
        Customer customerTwo = new Customer(UUID.randomUUID(), "pobi", "pobi@gmail.com", LocalDateTime.now());
        jdbcCustomerRepository.save(customerOne);
        jdbcCustomerRepository.save(customerTwo);
        // when
        // then
        customerTwo.changeEmail(customerOne.getEmail());
        assertThatThrownBy(() -> jdbcCustomerRepository.update(customerTwo))
            .isInstanceOf(DuplicateEmailException.class)
            .hasMessage("[ERROR] 중복된 이메일이 존재합니다.");
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
