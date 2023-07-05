package org.prgms.vouchermanagement.customer.domain.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.prgms.vouchermanagement.customer.domain.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerNamedJdbcRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);

    @Configuration
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3308/test_customer")
                    .username("test")
                    .password("1234")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public CustomerNamedJdbcRepository customerNamedJdbcRepository(DataSource dataSource) {
            return new CustomerNamedJdbcRepository(dataSource);
        }

    }

    @Autowired
    DataSource dataSource;

    @Autowired
    CustomerNamedJdbcRepository customerNamedJdbcRepository;

    Customer newCustomer;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(3308)
                .withUser("test", "1234")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test_customer", classPathScript("CustomerSchema.sql"))
                .start();

        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        logger.info("================= {}", newCustomer.getCreatedAt());
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("Customer ConnectionPool 확인")
    void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName()).isEqualTo("com.zaxxer.hikari.HikariDataSource");
    }

    @Test
    @Order(2)
    @DisplayName("고객 추가 기능 test")
    void testInsert() {
        customerNamedJdbcRepository.insert(newCustomer);

        Optional<Customer> retrievedCustomer = customerNamedJdbcRepository.findByID(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty()).isFalse();
        assertThat(retrievedCustomer.get()).usingRecursiveComparison().isEqualTo(newCustomer);
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객 조회 기능 test")
    void testFindAll() {
        List<Customer> customers = customerNamedJdbcRepository.findAll();
        assertThat(customers.isEmpty()).isFalse();
    }

    @Test
    @Order(4)
    @DisplayName("이름으로 고객 조회 기능 test")
    void testFindByName() {
        Optional<Customer> customer = customerNamedJdbcRepository.findByName(newCustomer.getName());
        assertThat(customer.isEmpty()).isFalse();

        Optional<Customer> unknown = customerNamedJdbcRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty()).isTrue();
    }

    @Test
    @Order(5)
    @DisplayName("이메일로 고객 조회 기능 test")
    void testFindByEmail() {
        Optional<Customer> customer = customerNamedJdbcRepository.findByEmail(newCustomer.getEmail());
        assertThat(customer.isEmpty()).isFalse();

        Optional<Customer> unknown = customerNamedJdbcRepository.findByEmail("unknown-user@gmail.com");
        assertThat(unknown.isEmpty()).isTrue();
    }

    @Test
    @Order(6)
    @DisplayName("고객 수정 기능 test")
    void testUpdate() {
        newCustomer.changeName("updated-user");
        customerNamedJdbcRepository.update(newCustomer);

        List<Customer> all = customerNamedJdbcRepository.findAll();
        MatcherAssert.assertThat(all, hasSize(1));
        MatcherAssert.assertThat(all, everyItem(samePropertyValuesAs(newCustomer)));

        Optional<Customer> retrievedCustomer = customerNamedJdbcRepository.findByID(newCustomer.getCustomerId());
        MatcherAssert.assertThat(retrievedCustomer.isEmpty(), is(false));
        MatcherAssert.assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }
}