package org.prgms.voucheradmin.domain.customer.dao.customer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcCustomerRepositoryTest {
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgms.voucheradmin"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            var datasource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher-admin")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();

            return datasource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    CustomerRepository jdbcCustomerRepository;

    EmbeddedMysql embeddedMysql;

    Customer customer = new Customer(UUID.randomUUID(), "test1", "test1@test.com", LocalDateTime.now());

    @BeforeAll
    void setUp() {
        var mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-voucher-admin", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("고객 생성 확인")
    void testCrateCustomer() {
        jdbcCustomerRepository.create(customer);

        List<Customer> customers = jdbcCustomerRepository.findAll();

        assertThat(customers.size(), is(1));
    }

    @Test
    @Order(2)
    @DisplayName("고객 조회 by email 확인")
    void testFindById() {
        Optional<Customer> retrievedCustomer = jdbcCustomerRepository.findById(customer.getCustomerId());
        assertThat(retrievedCustomer, not(is(Optional.empty())));
    }

    @Test
    @Order(3)
    @DisplayName("고객 조회 by id 확인")
    void testFindByEmail() {
        Optional<Customer> retrievedCustomer = jdbcCustomerRepository.findByEmail(customer.getEmail());
        assertThat(retrievedCustomer, not(is(Optional.empty())));
    }

    @Test
    @Order(4)
    @DisplayName("고객 업데이트 확인")
    void testUpdate() {
        jdbcCustomerRepository.update(new Customer(customer.getCustomerId(), "test2", customer.getEmail(), customer.getCreatedAt()));
        Optional<Customer> retrievedCustomer = jdbcCustomerRepository.findById(customer.getCustomerId());

        assertThat(retrievedCustomer, not(is(Optional.empty())));
        assertThat(retrievedCustomer.get().getName(), is("test2"));
    }
}