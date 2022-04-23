package com.pppp0722.vouchermanagement.voucher.repository;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v5_7_latest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import com.pppp0722.vouchermanagement.voucher.model.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("JdbcVoucherRepository 단위 테스트")
class JdbcVoucherRepositoryTest {

    @Configuration
    @ComponentScan
    static class config {

        @Bean
        public DataSource dataSource() {
            HikariDataSource dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:2216/test-voucher_mgmt")
                .username("test")
                .password("test1234!")
                .type(HikariDataSource.class)
                .build();

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
    }

    @Autowired
    JdbcVoucherRepository voucherRepository;

    EmbeddedMysql embeddedMysql;

    Voucher newVoucher;

    @BeforeAll
    void setup() {
        MysqldConfig mysqldConfig = aMysqldConfig(v5_7_latest)
            .withCharset(UTF8)
            .withPort(2216)
            .withUser("test", "test1234!")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(mysqldConfig)
            .addSchema("test-voucher_mgmt", classPathScript("schema.sql"))
            .start();

        newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10, UUID.randomUUID());
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("createVoucher() 테스트")
    public void testCreateVoucher() {
        Optional<Voucher> voucher = voucherRepository.createVoucher(newVoucher);

        if (voucher.isEmpty()) {
            assertThat(false, is(true));
        }

        Optional<Voucher> readVoucher = voucherRepository.readVoucher(
            newVoucher.getVoucherId());

        if (readVoucher.isEmpty()) {
            assertThat(false, is(true));
        }

        assertThat(readVoucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @Order(2)
    @DisplayName("readVouchers() 테스트")
    public void testReadVouchers() {
        List<Voucher> vouchers = voucherRepository.readAllVouchers();
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @Order(3)
    @DisplayName("readVoucher() 테스트")
    public void testReadVoucher() {
        Optional<Voucher> voucher = voucherRepository.readVoucher(newVoucher.getVoucherId());

        if (voucher.isEmpty()) {
            assertThat(false, is(true));
        }

        assertThat(voucher.get(), samePropertyValuesAs(newVoucher));
    }

    @Test
    @Order(4)
    @DisplayName("readVouchersByMemberId() 테스트")
    public void testReadVouchersByMemberId() {
        List<Voucher> vouchers = voucherRepository.readVouchersByMemberId(newVoucher.getMemberId());
        assertThat(vouchers.isEmpty(), is(false));
    }

    @Test
    @Order(5)
    @DisplayName("updateVoucher() 테스트")
    public void testUpdateVoucher() {
        Voucher updatedVoucher = new PercentDiscountVoucher(newVoucher.getVoucherId(), 20,
            newVoucher.getMemberId());

        Optional<Voucher> member = voucherRepository.updateVoucher(updatedVoucher);

        if (member.isEmpty()) {
            assertThat(false, is(true));
        }

        assertThat(member.get(), not(samePropertyValuesAs(newVoucher)));
    }

    @Test
    @Order(6)
    @DisplayName("deleteVoucher() 테스트")
    public void testDeleteVoucher() {
        Optional<Voucher> deletedVoucher = voucherRepository.deleteVoucher(newVoucher);

        if (deletedVoucher.isEmpty()) {
            assertThat(false, is(true));
        }

        Optional<Voucher> retrievedVoucher = voucherRepository.readVoucher(deletedVoucher.get()
            .getVoucherId());

        assertThat(retrievedVoucher.isEmpty(), is(true));
    }

    @Test
    @Order(7)
    @DisplayName("readVoucher() voucher 존재 X 예외 테스트")
    public void testReadVoucherException() {
        Optional<Voucher> voucher = voucherRepository.readVoucher(newVoucher.getVoucherId());
        assertThat(voucher.isEmpty(), is(true));
    }

    @Test
    @Order(8)
    @DisplayName("readVouchersByMemberId() voucher 존재 X 예외 테스트")
    public void testReadVouchersByMemberIdException() {
        List<Voucher> vouchers = voucherRepository.readVouchersByMemberId(newVoucher.getMemberId());
        assertThat(vouchers.isEmpty(), is(true));
    }

    @Test
    @Order(9)
    @DisplayName("updateVoucher() voucher 존재 X 예외 테스트")
    public void testUpdateVoucherException() {
        Voucher updatedVoucher = new PercentDiscountVoucher(newVoucher.getVoucherId(), 20,
            newVoucher.getMemberId());
        Optional<Voucher> member = voucherRepository.updateVoucher(updatedVoucher);
        assertThat(member.isEmpty(), is(true));
    }

    @Test
    @Order(10)
    @DisplayName("deleteVoucher() voucher 존재 X 예외 테스트")
    public void testDeleteVoucherException() {
        Optional<Voucher> voucher = voucherRepository.deleteVoucher(newVoucher);
        assertThat(voucher.isEmpty(), is(true));
    }
}