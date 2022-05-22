package org.prgms.voucheradmin.domain.voucher.dao;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgms.voucheradmin.domain.customer.dao.customer.CustomerRepository;
import org.prgms.voucheradmin.domain.customer.entity.Customer;
import org.prgms.voucheradmin.domain.voucher.entity.FixedAmountVoucher;
import org.prgms.voucheradmin.domain.voucher.entity.PercentageDiscountVoucher;
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
class voucherRepositoryTest {
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
    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10, LocalDateTime.now().minusDays(3));
    Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000, LocalDateTime.now().minusDays(2));
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
    @DisplayName("바우처 생성 확인")
    void testCreationVoucher() throws Throwable{
        voucherRepository.create(voucher);

        List<Voucher> vouchers = voucherRepository.findAll();

        assertThat(vouchers.size(), is(1));
    }

    @Test
    @Order(2)
    @DisplayName("바우처 수정 확인")
    void testUpdateVoucher() throws Throwable{
        voucherRepository.update(new PercentageDiscountVoucher(voucher.getVoucherId(), 20, voucher.getCreatedAt()));

        List<Voucher> vouchers = voucherRepository.findAll();

        assertThat(vouchers.get(0).getAmount(), is(20L));
        assertThat(vouchers.get(0).getVoucherType(), is(PERCENTAGE_DISCOUNT));
    }

    @Test
    @Order(3)
    @DisplayName("바우처 id 조회 확인")
    void testFindById() throws Throwable{
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(voucher.getVoucherId());

        assertThat(retrievedVoucher, not(is(Optional.empty())));
    }

    @Test
    @Order(4)
    @DisplayName("바우처 삭제 확인")
    void testDeleteById() throws Throwable{
        voucherRepository.delete(voucher);

        List<Voucher> vouchers = voucherRepository.findAll();

        assertThat(vouchers.size(), is(0));
    }

    @Test
    @Order(5)
    @DisplayName("고객에게 할당된 바우처 조회")
    void testFindAllocatedVouchers() throws IOException {
        customerRepository.create(customer);
        voucherRepository.create(voucher);
        voucherRepository.create(voucher1);
        voucherRepository.create(voucher2);
        voucherWalletRepository.create(voucherWallet1);
        voucherWalletRepository.create(voucherWallet2);

        List<Voucher> vouchers = voucherRepository.findAllocatedVouchers(customer.getCustomerId());

        assertThat(vouchers.size(), is(2));
    }


    @Test
    @Order(6)
    @DisplayName("바우처 타입으로 조회")
    void testFindAllWithVoucherType() {
        List<Voucher> vouchers = voucherRepository.findAllWithVoucherType(FIXED_AMOUNT);
        assertThat(vouchers.size(), is(2));
        assertThat(vouchers.get(0).getVoucherType(), is(FIXED_AMOUNT));

    }

    @Test
    @Order(7)
    @DisplayName("생성일로 조회")
    void testFindAllWithDate() {
        List<Voucher> vouchers = voucherRepository.findAllWithDate(LocalDate.now().minusDays(2), LocalDate.now());
        assertThat(vouchers.size(), is(2));
    }

    @Test
    @Order(8)
    @DisplayName("바우처 타입 + 생성일 조회")
    void testFindAllWithVoucherTypeAndDate() {
        List<Voucher> vouchers = voucherRepository.findAllWithVoucherTypeAndDate(FIXED_AMOUNT, LocalDate.now().minusDays(2), LocalDate.now());
        assertThat(vouchers.size(), is(1));
    }
}