package com.programmers.VoucherManagementApplication.customer;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

import static org.assertj.core.api.Assertions.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

    @Configuration
    @ComponentScan(
            basePackages = {"com.programmers.VoucherManagementApplication.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2210/test-order_mgmt")
                    .username("test")
                    .password("test1234")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    CustomerJdbcRepository customerJdbcRepository;

    @Autowired
    DataSource dataSource;

    Customer newCustomer;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        newCustomer = new Customer(UUID.randomUUID(), "intellij-test", "intellij-user@gmail.com", LocalDateTime.now());
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2210)
                .withUser("test", "test1234")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("customers.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    @Order(1)
    public void testInsert() {
        customerJdbcRepository.insert(newCustomer);

        Optional<Customer> retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());

        assertThat(retrievedCustomer.isEmpty()).isFalse();
        assertThat(retrievedCustomer.get().getCustomerId()).isEqualTo(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.get().getName()).isEqualTo(newCustomer.getName());
        assertThat(retrievedCustomer.get().getEmail()).isEqualTo(newCustomer.getEmail());
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    @Order(2)
    public void testFindAll() {
        List<Customer> all = customerJdbcRepository.findAll();

        assertThat(all.isEmpty()).isFalse();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("등록된 이름 입력시 해당 고객을 조회할 수 있다.")
    @Order(3)
    public void testFindByName() {
        Optional<Customer> findByName = customerJdbcRepository.findByName(newCustomer.getName());
        assertThat(findByName.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("등록되지 않은 이름 입력시 고객을 조회할 수 없다.")
    @Order(4)
    public void testFindByUnknownName() {
        Optional<Customer> unKnownName = customerJdbcRepository.findByName("unknown-user");
        assertThat(unKnownName.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("이메일로 고객을 조회할 수 있다.")
    @Order(5)
    public void testFindByEmail() {
        var customers = customerJdbcRepository.findByEmail(newCustomer.getEmail());
        assertThat(customers.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("등록되지 않은 이메일 입력시 고객을 조회할 수 없다.")
    @Order(6)
    public void testFindByUnknownEmail() {
        var unknown = customerJdbcRepository.findByEmail("unknown-user@gmail.com");
        assertThat(unknown.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    @Order(7)
    public void testUpdate() {
        newCustomer.changeName("update-name");
        customerJdbcRepository.update(newCustomer);

        List<Customer> findCustomer = customerJdbcRepository.findAll();
        assertThat(findCustomer).isNotEmpty();
        assertThat(findCustomer.size()).isEqualTo(1);

        Optional<Customer> retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty()).isFalse();
        assertThat(retrievedCustomer.get().getName()).isEqualTo(newCustomer.getName());
    }

    @Test
    @DisplayName("올바르지 않은 이름 입력시 고객을 수정할 수 없다.")
    @Order(8)
    public void testUpdate_fail() {
        assertThatThrownBy(() -> {
            newCustomer.changeName(" ");
        }).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("전체 고객을 삭제할 수 있다.")
    @Order(9)
    public void testDeleteAll() {
        assertThat(customerJdbcRepository.count()).isEqualTo(1);
        customerJdbcRepository.deleteAll();
        assertThat(customerJdbcRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("등록된 아이디 입력시 해당 고객을 삭제할 수 있다.")
    @Order(10)
    public void testDeleteById() {
        Customer customer = new Customer(UUID.randomUUID(), "delete-name", "delete-user@gmail.com", LocalDateTime.now());
        customerJdbcRepository.insert(customer);
        assertThat(customerJdbcRepository.count()).isEqualTo(1);
        customerJdbcRepository.deleteById(customer.getCustomerId());
        assertThat(customerJdbcRepository.count()).isEqualTo(0);
    }
}