package org.prgrms.part1.engine.service;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.part1.engine.domain.Customer;
import org.prgrms.part1.engine.domain.Voucher;
import org.prgrms.part1.engine.enumtype.VoucherType;
import org.prgrms.part1.engine.repository.CustomerRepository;
import org.prgrms.part1.engine.repository.JdbcCustomerRepository;
import org.prgrms.part1.engine.repository.VoucherRepository;
import org.prgrms.part1.engine.repository.JdbcVoucherRepository;
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
class VoucherServiceTest {
    @Configuration
    @ComponentScan(basePackages = {"org.prgrms.part1.engine", "org.prgrms.part1.exception"})
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
    VoucherRepository voucherRepository;

    @Autowired
    VoucherService voucherService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    EmbeddedMysql embeddedMysql;

    Voucher fVoucher;
    Voucher pVoucher;
    Customer customer;

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

    @BeforeEach
    void setupEach() {
        fVoucher = VoucherType.FIXED_AMOUNT.createVoucher(UUID.randomUUID(), 5000, LocalDateTime.now().withNano(0));
        pVoucher = VoucherType.PERCENT_DISCOUNT.createVoucher(UUID.randomUUID(), 50, LocalDateTime.now().withNano(0));
        customer = customerService.createCustomer("test0", "test0@gmail.com");
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @AfterEach
    void cleanTable() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("생성한 Voucher를 DB에 삽입할 수 있다.")
    public void testVoucherService() {
        voucherService.insertVoucher(fVoucher);
        voucherService.insertVoucher(pVoucher);

        Voucher findFVoucher = voucherService.getVoucher(fVoucher.getVoucherId());
        Voucher findPVoucher = voucherService.getVoucher(pVoucher.getVoucherId());

        assertThat(findFVoucher, samePropertyValuesAs(fVoucher));
        assertThat(findPVoucher, samePropertyValuesAs(pVoucher));

        List<Voucher> vouchers = voucherService.getAllVouchers();
        assertThat(vouchers.size(), is(2));
    }

    @Test
    @DisplayName("Customer에 Voucher를 allocate / deallocate 할 수 있다.")
    public void testAllocateAndDeallocate() {
        voucherService.insertVoucher(fVoucher);

        voucherService.allocateToCustomer(fVoucher, customer);

        Voucher findFVoucher = voucherService.getVoucher(fVoucher.getVoucherId());

        assertThat(findFVoucher.getCustomerId().isEmpty(), is(false));
        assertThat(findFVoucher.getCustomerId().get(), is(customer.getCustomerId()));

        voucherService.deallocateFromCustomer(fVoucher);
        findFVoucher = voucherService.getVoucher(fVoucher.getVoucherId());

        assertThat(findFVoucher.getCustomerId().isEmpty(), is(true));
    }

    @Test
    @DisplayName("Customer에 여러 개의 Voucher를 allocate 할 수 있다.")
    public void testAllocateVouchers() {
        voucherService.insertVoucher(fVoucher);
        voucherService.insertVoucher(pVoucher);

        voucherService.allocateToCustomer(fVoucher, customer);
        voucherService.allocateToCustomer(pVoucher, customer);

        List<Voucher> vouchers = voucherService.getVouchersByCustomer(customer);

        assertThat(vouchers.size(), is(2));
    }

    @Test
    @DisplayName("Voucher를 삭제할 수 있다.")
    public void testRemove() {
        voucherService.insertVoucher(fVoucher);
        voucherService.insertVoucher(pVoucher);

        voucherService.removeVoucher(fVoucher);
        voucherService.removeVoucher(pVoucher);

        List<Voucher> allVouchers = voucherService.getAllVouchers();
        assertThat(allVouchers, hasSize(0));
    }
}