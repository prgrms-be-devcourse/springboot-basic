package org.prgms.voucheradmin.domain.customer.dao.customer;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
import org.prgms.voucheradmin.domain.voucherwallet.dao.VoucherWalletRepository;
import org.prgms.voucheradmin.domain.voucherwallet.entity.VoucherWallet;
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
class customerRepositoryTest {
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
    CustomerRepository customerRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherWalletRepository voucherWalletRepository;

    EmbeddedMysql embeddedMysql;

    Customer customer = Customer.builder()
            .customerId(UUID.randomUUID())
            .name("test1")
            .email("test1@test.com")
            .createdAt(LocalDateTime.now())
            .build();

    Customer customer2 = Customer.builder()
            .customerId(UUID.randomUUID())
            .name("tester")
            .email("tester@gmail.com")
            .createdAt(LocalDateTime.now())
            .build();

    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
    VoucherWallet voucherWallet1 = new VoucherWallet(UUID.randomUUID(), customer.getCustomerId(), voucher.getVoucherId());
    VoucherWallet voucherWallet2 = new VoucherWallet(UUID.randomUUID(), customer2.getCustomerId(), voucher.getVoucherId());

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
        customerRepository.create(customer);

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers.size(), is(1));
    }

    @Test
    @Order(2)
    @DisplayName("고객 조회 by email 확인")
    void testFindById() {
        Optional<Customer> retrievedCustomer = customerRepository.findById(customer.getCustomerId());
        assertThat(retrievedCustomer, not(is(Optional.empty())));
    }

    @Test
    @Order(3)
    @DisplayName("고객 조회 by id 확인")
    void testFindByEmail() {
        Optional<Customer> retrievedCustomer = customerRepository.findByEmail(customer.getEmail());
        assertThat(retrievedCustomer, not(is(Optional.empty())));
    }

    @Test
    @Order(4)
    @DisplayName("고객 업데이트 확인")
    void testUpdate() {

        customerRepository.update(
                Customer.builder()
                        .customerId(customer.getCustomerId())
                        .name("test2")
                        .email(customer.getEmail())
                        .createdAt(customer.getCreatedAt())
                        .build()
        );
        Optional<Customer> retrievedCustomer = customerRepository.findById(customer.getCustomerId());

        assertThat(retrievedCustomer, not(is(Optional.empty())));
        assertThat(retrievedCustomer.get().getName(), is("test2"));
    }

    @Test
    @Order(5)
    @DisplayName("고객 제거 확인")
    void testDelete() {
        customerRepository.delete(customer);
        Optional<Customer> retrievedCustomer = customerRepository.findById(customer.getCustomerId());

        assertThat(retrievedCustomer, is(Optional.empty()));
    }

    @Test
    @Order(6)
    @DisplayName("특정 부우처를 보유한 고객 조회 확인")
    void testFindVoucherOwners() throws IOException {
        customerRepository.create(customer);
        customerRepository.create(customer2);
        voucherRepository.create(voucher);
        voucherWalletRepository.create(voucherWallet1);
        voucherWalletRepository.create(voucherWallet2);

        List<Customer> customers = customerRepository.findVoucherOwners(voucher.getVoucherId());

        assertThat(customers.size(), is(2));
    }
}