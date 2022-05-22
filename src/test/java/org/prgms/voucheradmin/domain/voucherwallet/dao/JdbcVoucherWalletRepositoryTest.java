package org.prgms.voucheradmin.domain.voucherwallet.dao;

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
import org.prgms.voucheradmin.domain.customer.dao.customer.CustomerRepository;
import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.domain.voucher.dao.VoucherRepository;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.Voucher;
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
class JdbcVoucherWalletRepositoryTest {
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
            .name("tester")
            .email("tester@gmail.com")
            .createdAt(LocalDateTime.now())
            .build();
    Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000, LocalDateTime.now());
    Voucher voucher2 = new PercentageDiscountVoucher(UUID.randomUUID(), 50, LocalDateTime.now());
    VoucherWallet voucherWallet1 = new VoucherWallet(UUID.randomUUID(), customer.getCustomerId(), voucher1.getVoucherId());
    VoucherWallet voucherWallet2 = new VoucherWallet(UUID.randomUUID(), customer.getCustomerId(), voucher2.getVoucherId());

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
    @DisplayName("바우처 지갑 생성 확인")
    void testWalletCreation() throws IOException {
        customerRepository.create(customer);
        voucherRepository.create(voucher1);
        voucherRepository.create(voucher2);

        voucherWalletRepository.create(voucherWallet1);
        voucherWalletRepository.create(voucherWallet2);

        List<VoucherWallet> voucherWallets = voucherWalletRepository.findAll();

        assertThat(voucherWallets.size(), is(2));
    }

    @Test
    @Order(2)
    @DisplayName("바우처 지갑 조회 확인")
    void testWalletFindByCustomerIdAndVoucherId() {
        Optional<VoucherWallet> retrievedVoucherWallet1 = voucherWalletRepository
                .findByCustomerIdAndVoucherId(voucherWallet1.getCustomerId(), voucherWallet1.getVoucherId());
        Optional<VoucherWallet> retrievedVoucherWallet2 = voucherWalletRepository
                .findByCustomerIdAndVoucherId(UUID.randomUUID(), voucherWallet2.getVoucherId());

        assertThat(retrievedVoucherWallet1, not(is(Optional.empty())));
        assertThat(retrievedVoucherWallet2, is(Optional.empty()));
    }

    @Test
    @Order(3)
    @DisplayName("바우처 지갑 삭제 확인")
    void testWalletDelete() {
        voucherWalletRepository.deleteVoucherWallet(voucherWallet1);
        voucherWalletRepository.deleteVoucherWallet(voucherWallet2);

        List<VoucherWallet> voucherWallets = voucherWalletRepository.findAll();

        assertThat(voucherWallets.size(), is(0));
    }
}