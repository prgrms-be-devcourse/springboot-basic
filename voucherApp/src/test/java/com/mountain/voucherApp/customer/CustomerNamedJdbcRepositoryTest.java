package com.mountain.voucherApp.customer;


import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.jdbc.BadSqlGrammarException;
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
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerNamedJdbcRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(CustomerNamedJdbcRepositoryTest.class);

    @Configuration
    @ComponentScan(
            basePackages = {"com.mountain.voucherApp.customer"}
    )
    static class Config {
        @Bean
        public DataSource dataSource() {
            String URL = "jdbc:mysql://localhost:2215/test-order-mgmt";
            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url(URL)
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
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

    @Autowired
    CustomerNamedJdbcRepository customerNamedJdbcRepository;

    @Autowired
    DataSource dataSource;

    Customer customer;
    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        customer = new Customer(UUID.randomUUID(),
                "test-user",
                "test-user@gmail.com",
                LocalDateTime.now());

        MysqldConfig config = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("test-order-mgmt", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @Description("dataSource가 정상적으로 Autowired되었는지 확인.")
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @DisplayName("고객을 추가할 수 있다.")
    @Order(2)
    public void testInsert() throws Exception {
        try {
            customerNamedJdbcRepository.insert(customer);
        } catch (BadSqlGrammarException e) {
            log.info("Got BadSqlGrammarException error code -> {}", e.getSQLException().getErrorCode(), e);
        }
        Optional<Customer> savedCustomer = customerNamedJdbcRepository.findById(customer.getCustomerId());
        assertThat(savedCustomer.isEmpty(), is(false));
        assertThat(savedCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("전체 고객 조회.")
    @Order(3)
    public void testFindAll() throws Exception {
        List<Customer> customers = customerNamedJdbcRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @DisplayName("이름으로 고객 조회.")
    @Order(4)
    public void testFindByName() {
        Optional<Customer> getCustomer = customerNamedJdbcRepository.findByName(customer.getName());
        assertThat(getCustomer.isEmpty(), is(false));

        Optional<Customer> unknown = customerNamedJdbcRepository.findByName("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("이메일로 고객 조회.")
    @Order(5)
    public void testFindByEmail() throws Exception {
        Optional<Customer> getCustomer = customerNamedJdbcRepository.findByEmail(customer.getEmail());
        assertThat(getCustomer.isEmpty(), is(false));

        Optional<Customer> unknown = customerNamedJdbcRepository.findByEmail("unknown-user");
        assertThat(unknown.isEmpty(), is(true));
    }

    @Test
    @DisplayName("고객을 수정할 수 있다.")
    @Order(6)
    public void testUpdate() throws Exception {
        customer.changeName("updated-user");
        customerNamedJdbcRepository.update(customer);

        List<Customer> customers = customerNamedJdbcRepository.findAll();

        assertThat(customers, hasSize(1));
        assertThat(customers, everyItem(samePropertyValuesAs(customer)));

        Optional<Customer> savedCustomer = customerNamedJdbcRepository.findById(customer.getCustomerId());
        assertThat(savedCustomer.isEmpty(), is(false));
        assertThat(savedCustomer.get(), samePropertyValuesAs(customer));
    }

}