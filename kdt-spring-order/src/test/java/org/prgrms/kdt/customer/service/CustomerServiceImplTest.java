package org.prgrms.kdt.customer.service;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.customer.domain.Customer;
import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("database")
class CustomerServiceImplTest {
    @Configuration
    @ComponentScan(basePackages = "org.prgrms.kdt.customer")
    @EnableTransactionManagement
    static class Config {
        @Bean
        public DataSource dataSource() {
            var dataSource = DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-order_mgmt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
            return dataSource;
        }

        @Bean
        JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate){
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    static EmbeddedMysql embeddedMysql;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @BeforeAll
    static void setUp(){
        MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
                .withCharset(UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("test-order_mgmt", classPathScript("create_customer_table.sql"))
                .start();
    }

    @AfterAll
    static void afterAll(){
        embeddedMysql.stop();
    }

    @AfterEach
    void afterEach(){
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("여러 고객을 추가한다.")
    void InsertingMultiCustomersTest(){
        //given
        List<Customer> customers = List.of(
                new Customer(UUID.randomUUID(), "a", "a@gmail.com", LocalDateTime.now(), false),
                new Customer(UUID.randomUUID(), "b", "b@gmail.com", LocalDateTime.now(), false)
        );

        //when
        customerService.createCustomers(customers);

        //then
        List<Customer> all = customerRepository.findAll();
        assertThat(all.size(), is(2));
        assertThat(all, containsInAnyOrder(samePropertyValuesAs(customers.get(0)), samePropertyValuesAs(customers.get(1))));
    }

    @Test
    @DisplayName("고객 추가 시 오류가 발생하면 해당 트랜잭션을 롤백(해당 작업이 실행되기 전 상태로 복원)한다.")
    void InsertingCustomersHavingDuplicatedEmailTest(){
        //given
        List<Customer> customers = List.of(
                new Customer(UUID.randomUUID(), "c", "c@gmail.com", LocalDateTime.now(), false),
                new Customer(UUID.randomUUID(), "d", "c@gmail.com", LocalDateTime.now(), false)
        );

        //when
        try {
            customerService.createCustomers(customers);
        }
        catch (DataAccessException e){}

        //then
        List<Customer> all = customerRepository.findAll();
        assertThat(all.size(), is(0));
        assertThat(all.isEmpty(), is(true));
        assertThat(all, not(containsInAnyOrder(samePropertyValuesAs(customers.get(0)), samePropertyValuesAs(customers.get(1)))));
    }

    @Test
    @DisplayName("모든 고객을 조회한다.")
    void getAllCouchersTest(){
        //given
        List<Customer> newCustomers = List.of(
                new Customer(UUID.randomUUID(), "a", "a@gmail.com", LocalDateTime.now(), false),
                new Customer(UUID.randomUUID(), "b", "b@gmail.com", LocalDateTime.now(), false),
                new Customer(UUID.randomUUID(), "c", "c@gmail.com", LocalDateTime.now(), false),
                new Customer(UUID.randomUUID(), "d", "d@gmail.com", LocalDateTime.now(), false)
        );
        customerService.createCustomers(newCustomers);

        //when
        List<Customer> customers = customerService.getAllCustomers();

        //then
        assertThat(customers.size(), is(newCustomers.size()));
        assertThat(customers, containsInAnyOrder(samePropertyValuesAs(newCustomers.get(0)),
                                                samePropertyValuesAs(newCustomers.get(1)),
                                                samePropertyValuesAs(newCustomers.get(2)),
                                                samePropertyValuesAs(newCustomers.get(3))
        ));

    }
}