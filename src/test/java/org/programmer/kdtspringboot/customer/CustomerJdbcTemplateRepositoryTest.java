package org.programmer.kdtspringboot.customer;

import com.wix.mysql.EmbeddedMysql;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcTemplateRepositoryTest {

    @Configuration
    @ComponentScan(basePackages = {"org.programmer.kdtspringboot.customer"})
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    CustomerJdbcTemplateRepository customerJdbcTemplateRepository;

    @Autowired
    DataSource dataSource;

    Customer customer;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        //임베디드 실습
        customer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com", LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        var mysqlConfig = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void tearDown() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("HiKARI 확인")
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있음")
    void testInsertCustomer() {
        customerJdbcTemplateRepository.insert(customer);

        var retrievedCustomer = customerJdbcTemplateRepository.findById(customer.getCustomerId());
        assertThat(retrievedCustomer).isNotEmpty();
        assertThat(retrievedCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객을 조회할 수 있음")
    void testFindAll() /*throws InterruptedException*/ {
        var customers = customerJdbcTemplateRepository.findAll();
        assertThat(customers).isNotEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("이름으로 고객을 조회할 수 있음")
    void testFindName() {
        var customers = customerJdbcTemplateRepository.findByName(customer.getName());
        assertThat(customers).isNotEmpty();

        var customers1 = customerJdbcTemplateRepository.findByName("unknown-user");
        assertThat(customers1).isEmpty();
    }

    @Test
    @Order(5)
    @DisplayName("이메일로 고객을 조회할 수 있음")
    void testFindEmail() {
        var customers = customerJdbcTemplateRepository.findByEmail(customer.getEmail());
        assertThat(customers).isNotEmpty();

        var customers1 = customerJdbcTemplateRepository.findByName("unknown-user@123");
        assertThat(customers1).isEmpty();
    }

    @Test
    @Order(6)
    @DisplayName("고객을 수정할 수 있음")
    void testUpdateCustomer() {
        customer.changeName("updated-user");
        customerJdbcTemplateRepository.update(customer);

        var customers = customerJdbcTemplateRepository.findAll();
        assertThat(customers).isNotEmpty();
        assertThat(customers).hasSize(1);

        var retrivedCustomer = customerJdbcTemplateRepository.findById(customer.getCustomerId());
        assertThat(retrivedCustomer).isNotEmpty();
        assertThat(retrivedCustomer.get()).usingRecursiveComparison().isEqualTo(customer);
    }
}
