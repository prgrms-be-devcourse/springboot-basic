package com.voucher.vouchermanagement.service.customer;

import com.voucher.vouchermanagement.dto.customer.CustomerDto;
import com.voucher.vouchermanagement.dto.customer.CustomerJoinRequest;
import com.voucher.vouchermanagement.model.customer.Customer;
import com.voucher.vouchermanagement.repository.customer.CustomerJdbcRepository;
import com.voucher.vouchermanagement.repository.customer.CustomerRepository;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@ActiveProfiles("prod")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    private static EmbeddedMysql embeddedMysql;

    @Configuration
    @EnableTransactionManagement
    static class AppConfig {
        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:2215/test-voucher")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public CustomerRepository customerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new CustomerJdbcRepository(jdbcTemplate);
        }

        @Bean
        public CustomerService customerService(CustomerRepository customerRepository) {
            return new CustomerService(customerRepository);
        }

        @Bean
        PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @AfterEach
    public void cleanEach() {
        customerRepository.deleteAll();
    }

    @BeforeAll
    public void setup() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMysql = anEmbeddedMysql(mysqldConfig)
                .addSchema("test-voucher", classPathScript("schema.sql"))
                .start();
    }

    @Test
    public void joinTest() {
        //given
        String customerName = "jh";
        String customerEmail = "pjh_jn@naver.com";

        //when
        UUID joinedCustomerId = customerService.join(customerName, customerEmail);
        CustomerDto foundCustomer = customerService.findByName(customerName);

        //then
        assertThat(foundCustomer.getId(), is(joinedCustomerId));
        assertThat(foundCustomer.getName(), is(customerName));
        assertThat(foundCustomer.getEmail(), is(customerEmail));
    }

    @Test
    public void multiJoinTest() {
        //given
        List<CustomerJoinRequest> customerJoinRequests = List.of(
                new CustomerJoinRequest("customerA", "emailA@gmail.com"),
                new CustomerJoinRequest("customerB", "emailB@gmail.com"),
                new CustomerJoinRequest("customerC", "emailC@gmail.com")
        );

        //when
        customerService.multiJoin(customerJoinRequests);
        List<CustomerDto> foundCustomers = customerService.findAll();

        //then
        assertThat(foundCustomers.size(), is(3));
        assertThat(foundCustomers, containsInAnyOrder(samePropertyValuesAs(foundCustomers.get(0)),
                samePropertyValuesAs(foundCustomers.get(1)),
                samePropertyValuesAs(foundCustomers.get(2))
        ));
    }

    @Test
    public void findByIdTest() {
        //given
        String customerName = "jh";
        String customerEmail = "pjh_jn@naver.com";
        UUID joinedCustomerId = customerService.join(customerName, customerEmail);

        //when
        CustomerDto foundCustomer = customerService.findById(joinedCustomerId);

        //then
        assertThat(foundCustomer.getId(), is(joinedCustomerId));
        assertThat(foundCustomer.getName(), is(customerName));
        assertThat(foundCustomer.getEmail(), is(customerEmail));
    }

    @Test
    public void findByNameTest() {
        //given
        String customerName = "jh";
        String customerEmail = "pjh_jn@naver.com";
        UUID joinedCustomerId = customerService.join(customerName, customerEmail);

        //when
        CustomerDto foundCustomer = customerService.findByName(customerName);

        //then
        assertThat(foundCustomer.getId(), is(joinedCustomerId));
        assertThat(foundCustomer.getName(), is(customerName));
        assertThat(foundCustomer.getEmail(), is(customerEmail));
    }

    @Test
    public void findByEmailTest() {
        //given
        String customerName = "jh";
        String customerEmail = "pjh_jn@naver.com";
        UUID joinedCustomerId = customerService.join(customerName, customerEmail);

        //when
        CustomerDto foundCustomer = customerService.findByEmail(customerEmail);

        //then
        assertThat(foundCustomer.getId(), is(joinedCustomerId));
        assertThat(foundCustomer.getName(), is(customerName));
        assertThat(foundCustomer.getEmail(), is(customerEmail));
    }

    @Test
    public void findAllTest() {
        //given
        UUID joinedCustomerAId = customerService.join("customerA", "emailA@gmail.com");
        UUID joinedCustomerBId = customerService.join("customerB", "emailB@gmail.com");
        UUID joinedCustomerCId = customerService.join("customerC", "emailC@gmail.com");

        //when
        List<CustomerDto> customers = customerService.findAll();

        assertThat(customers.size(), is(3));
        assertThat(
                customers.stream()
                        .map(CustomerDto::getId)
                        .collect(Collectors.toList()),
                containsInAnyOrder(joinedCustomerAId, joinedCustomerBId, joinedCustomerCId)
        );
    }

    @Test
    public void duplicatedEmailTest() {
        //given
        customerService.join("customerA", "pjh_jn@naver.com");

        //when, then
        assertThrows(DuplicateKeyException.class, () -> customerService.join("customerB", "pjh_jn@naver.com"));
    }
}