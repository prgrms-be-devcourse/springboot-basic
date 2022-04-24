package org.prgrms.voucherapp.engine.customer.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.voucherapp.engine.customer.entity.Customer;
import org.prgrms.voucherapp.global.enums.CustomerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.everyItem;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcCustomerRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepositoryTest.class);
    @Configuration
    @ComponentScan(
            basePackages = {"org.prgrms.voucherapp"}
    )
    static class Config{
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/voucher_db")
                    .username("test")
                    .password("1234")
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
        public PlatformTransactionManager platformTransactionManager(DataSource dataSource){
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager){
            return new TransactionTemplate(platformTransactionManager);
        }
    }

    @Autowired
    JdbcCustomerRepository customerRepository;

    @Autowired
    DataSource dataSource;

    Customer customer;

    Customer customerWithStatus;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp(){
        customer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com");
        customerWithStatus = new Customer(UUID.randomUUID(), "test-user2", "test-user2@gmail.com", CustomerStatus.VIP.toString());
        MysqldConfig mySqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "1234")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mySqlConfig)
                .addSchema("voucher_db", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {embeddedMysql.stop();}

    @Test
    @Order(1)
    public void testHikariConnectionPool() {
        assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
    }

    @Test
    @Order(2)
    @DisplayName("고객을 추가할 수 있다.")
    public void testInsert() {
        customerRepository.insert(customer);
        Optional<Customer> retrievedCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @Order(3)
    @DisplayName("전체 고객을 조회할 수 있다.")
    public void testFindAll() {
        List<Customer> customers = customerRepository.findAll();
        assertThat(customers.isEmpty(), is(false));
    }

    @Test
    @Order(4)
    @DisplayName("고객의 이름을 수정할 수 있다.")
    public void testNameUpdate() {
        customer.changeName("updated user");
        customerRepository.update(customer);

        List<Customer> all = customerRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(customer)));

        Optional<Customer> retrievedCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @Order(4)
    @DisplayName("상태 없는 고객에게 상태를 부여할 수 있다.")
    public void giveStatusToCustomer() {
        customer.changeStatus("VIP");
        customerRepository.update(customer);

        List<Customer> all = customerRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(customer)));

        Optional<Customer> retrievedCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @Order(5)
    @DisplayName("고객의 상태를 수정할 수 있다.")
    public void updateCustomerStatus() {
        customer.changeStatus("wrong-status");
        customerRepository.update(customer);

        List<Customer> all = customerRepository.findAll();
        assertThat(all, hasSize(1));
        assertThat(all, everyItem(samePropertyValuesAs(customer)));

        Optional<Customer> retrievedCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(retrievedCustomer.isEmpty(), is(false));
        assertThat(retrievedCustomer.get().getStatus(), is(nullValue()));
        assertThat(retrievedCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @Order(5)
    @DisplayName("고객을 삭제할 수 있다.")
    public void testDelete() {
        customerRepository.deleteById(customer.getCustomerId());

        List<Customer> all = customerRepository.findAll();
        assertThat(all, hasSize(0));
    }

}