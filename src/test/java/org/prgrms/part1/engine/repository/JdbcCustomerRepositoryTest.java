package org.prgrms.part1.engine.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.part1.engine.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JdbcCustomerRepositoryTest {
    @Configuration
    @ComponentScan()
    static class Config {
        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                    .username("test")
                    .password("1q2w3e4r!")
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(1000);
            dataSource.setMinimumIdle(100);
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

        @Bean
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
            return new TransactionTemplate(transactionManager);
        }
    }

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcCustomerRepository customerRepository;

    Customer newCustomer;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
        newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com", LocalDateTime.now().withNano(0));
        MysqldConfig config = aMysqldConfig(v5_7_latest)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "1q2w3e4r!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order_mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    @Order(2)
    public void testInsert() {
        customerRepository.insert(newCustomer);
        Optional<Customer> retrievedCustomer = customerRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    @Order(3)
    public void testFindAll() {
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("이름으로 고객을 조회할 수 있다.")
    @Order(4)
    public void testFindByName() {
        Optional<Customer> customer = customerRepository.findByName(newCustomer.getName());
        assertThat(customer.isEmpty(), is(false));

        Optional<Customer> unknown = customerRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이메일으로 고객을 조회할 수 있다.")
    @Order(5)
    public void testFindByEmail() {
        Optional<Customer> customer = customerRepository.findByEmail(newCustomer.getEmail());
        assertThat(customer.isEmpty(), is(false));

        Optional<Customer> unknown = customerRepository.findByEmail("unknown-user@gmail.com");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @Order(6)
    @DisplayName("고객을 수정할 수 있다.")
    public void testUpdateCustomer() {
        newCustomer.changeName("updated-user");
        customerRepository.update(newCustomer);

        List<Customer> all = customerRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(newCustomer)));
        Optional<Customer> retrievedCustomer = customerRepository.findById(newCustomer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
    }

    @Test
    @Order(7)
    @DisplayName("고객을 Black 회원으로 지정하거나 원복시킬 수 있다..")
    public void testUpdateCustomerToBlack() {
        newCustomer.toBlack();
        customerRepository.update(newCustomer);

        List<Customer> blackList = customerRepository.findBlackStatus(true);
        List<Customer> whiteList = customerRepository.findBlackStatus(false);
        assertThat(blackList, hasSize(1));
        assertThat(whiteList, hasSize(0));

        newCustomer.revokeBlack();
        customerRepository.update(newCustomer);

        blackList = customerRepository.findBlackStatus(true);
        whiteList = customerRepository.findBlackStatus(false);
        assertThat(blackList, hasSize(0));
        assertThat(whiteList, hasSize(1));
    }

    @Test
    @Order(8)
    @DisplayName("고객을 삭제할 수 있다.")
    public void testDelete() {
        customerRepository.deleteById(newCustomer.getCustomerId());
        List<Customer> allCustomers = customerRepository.findAll();
        assertThat(allCustomers, hasSize(0));
    }
}