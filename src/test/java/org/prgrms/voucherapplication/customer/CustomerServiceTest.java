package org.prgrms.voucherapplication.customer;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);


    @Configuration
    @EnableTransactionManagement
    @ComponentScan(
            basePackages = {"org.prgrms.voucherapplication.customer"}
    )
    static class Config {

        @Bean
        public DataSource dataSource() {
//            EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
//                    .generateUniqueName(true)
//                    .setType(H2)
//                    .setScriptEncoding("UTF-8")
//                    .ignoreFailedDrops(true)
//                    .addScript("schema.sql")
//                    .build();

            HikariDataSource dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/voucher_application")
                    .username("test")
                    .password("test1234!")
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
        public TransactionTemplate transactionTemplate(PlatformTransactionManager platformTransactionManager) {
            return new TransactionTemplate(platformTransactionManager);
        }

        @Bean
        public CustomerRepository customerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new CustomerNamedJdbcRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public BlackListRepository blackListRepository() {
            return Mockito.mock(BlackListFileRepository.class);
        }

        @Bean
        public CustomerService customerService(CustomerRepository customerRepository, BlackListRepository blackListRepository) {
            return new CustomerServiceImpl(customerRepository, blackListRepository);
        }
    }

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        MysqldConfig mysqldConfig = MysqldConfig.aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)     // 임의의 포트 번호 지정
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("voucher_application", classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @AfterEach
    void dataCleanup() {
        customerRepository.deleteAll();
    }

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @DisplayName("다건 추가 테스트")
    void multiInsertTest() {
        List<Customer> customers = List.of(
                new Customer(UUID.randomUUID(), "a", "a@gmail.com", LocalDateTime.now()),
                new Customer(UUID.randomUUID(), "b", "b@gmail.com", LocalDateTime.now())
        );



        customerService.createCustomers(customers);
        List<Customer> all = customerRepository.findAll();
        assertThat(all.size(), is(2));
        assertThat(all, containsInAnyOrder(samePropertyValuesAs(customers.get(0)), samePropertyValuesAs(customers.get(1))));
    }

    @Test
    @DisplayName("다건 추가 실패시 전체 트랜잭션이 롤백되야한다.")
    void multiInsertRollbackTest() {
        List<Customer> customers = List.of(
                new Customer(UUID.randomUUID(), "c", "c@gmail.com", LocalDateTime.now()),
                new Customer(UUID.randomUUID(), "d", "c@gmail.com", LocalDateTime.now())
        );

        try {
            customerService.createCustomers(customers);
        } catch (DataAccessException e) {

        }
        List<Customer> all = customerRepository.findAll();
        assertThat(all.size(), is(0));
        assertThat(all.isEmpty(), is(true));
        assertThat(all, not(containsInAnyOrder(samePropertyValuesAs(customers.get(0)), samePropertyValuesAs(customers.get(1)))));
    }
}
