package org.prgrms.springbootbasic.engine.service;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.springbootbasic.engine.domain.Customer;
import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.enumtype.VoucherType;
import org.prgrms.springbootbasic.engine.repository.CustomerRepository;
import org.prgrms.springbootbasic.engine.repository.JdbcCustomerRepository;
import org.prgrms.springbootbasic.engine.repository.JdbcVoucherRepository;
import org.prgrms.springbootbasic.engine.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {
    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.springbootbasic.engine", "org.prgrms.springbootbasic.exception"})
    @EnableTransactionManagement
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
        public VoucherRepository voucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcVoucherRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public VoucherService voucherService(VoucherRepository voucherRepository) {
            return new VoucherService(voucherRepository);
        }

        @Bean
        public CustomerRepository customerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            return new JdbcCustomerRepository(namedParameterJdbcTemplate);
        }

        @Bean
        public CustomerService customerService(CustomerRepository customerRepository, VoucherService voucherService) {
            return new CustomerService(customerRepository, voucherService);
        }
    }
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    VoucherService voucherService;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setup() {
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

    @AfterEach
    void cleanTable() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Customer를 생성할 수 있다.")
    public void testCreateCustomer() {
        Customer customer = customerService.createCustomer("test0", "test0@gmail.com");
        Customer findCustomer = customerService.getCustomerById(customer.getCustomerId());

        assertThat(findCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("생성한 Customer를 DB에 삽입할 수 있다.")
    public void testInsertCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "test1", "test1@gmail.com", LocalDateTime.now().withNano(0));
        customerService.insertCustomer(customer);
        Customer findCustomer = customerService.getCustomerById(customer.getCustomerId());

        assertThat(findCustomer, samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("DB에서 Customer목록을 불러올 수 있다.")
    public void testGetAllCustomers() {
        Customer customer0 = customerService.createCustomer("test0", "test0@gmail.com");

        Customer customer1 = customerService.createCustomer("test1", "test1@gmail.com");

        List<Customer> customers = customerService.getAllCustomers();
        assertThat(customers.size(), is(2));
    }

    @Test
    @DisplayName("고객 및 해당 고객이 소유하고 있는 Voucher들을 삭제할 수 있다.")
    public void testDeleteCustomerAndOwnedVouchers() {
        Customer customer = customerService.createCustomer("test0", "test0@gmail.com");
        Voucher fVoucher = VoucherType.FIXED_AMOUNT.createVoucher(UUID.randomUUID(), 5000, LocalDateTime.now().withNano(0));
        Voucher pVoucher = VoucherType.PERCENT_DISCOUNT.createVoucher(UUID.randomUUID(), 50, LocalDateTime.now().withNano(0));
        voucherService.insertVoucher(fVoucher);
        voucherService.insertVoucher(pVoucher);
        voucherService.allocateToCustomer(fVoucher, customer);
        voucherService.allocateToCustomer(pVoucher, customer);

        customerService.removeCustomer(customer);

        List<Customer> allCustomers = customerService.getAllCustomers();
        List<Voucher> allVouchers = voucherService.getAllVouchers();
        assertThat(allCustomers, hasSize(0));
        assertThat(allVouchers, hasSize(0));
    }
}