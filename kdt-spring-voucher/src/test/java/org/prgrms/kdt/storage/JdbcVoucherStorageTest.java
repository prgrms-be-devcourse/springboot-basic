package org.prgrms.kdt.storage;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(value = "mysql")
class JdbcVoucherStorageTest {

    @Configuration
    @ComponentScan(
        basePackages = {"org.prgrms.kdt.storage", "org.prgrms.kdt.voucher"}
    )
    static class Config {
        @Autowired
        Environment environment;

        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                .url(environment.getProperty("mysql.url"))
                .username(environment.getProperty("mysql.username"))
                .password(environment.getProperty("mysql.password"))
                .type(HikariDataSource.class)
                .build();
        }
    }

    @Autowired
    JdbcVoucherStorage jdbcVoucherStorage;

    @BeforeEach
    void clearStorage() {
        jdbcVoucherStorage.clearStorage();
    }

    @Test
    @DisplayName("voucher를 저장할 수 있다.")
    void testSaveVoucher() {
        //given
        FixedAmountVoucher newFixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 40);
        UUID newVoucherId = newFixedAmountVoucher.getVoucherId();

        //when
        jdbcVoucherStorage.saveVoucher(newFixedAmountVoucher);

        //then
        Optional<Voucher> findVoucher = jdbcVoucherStorage.findById(newVoucherId);
        assertThat(findVoucher.isEmpty()).isFalse();
        assertThat(findVoucher.get().getVoucherId()).isEqualTo(newVoucherId);
    }

    @Test
    @DisplayName("저장된 모든 voucher들을 찾을 수 있다.")
    void testFindAllVoucher() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 50);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 70);
        jdbcVoucherStorage.saveVoucher(fixedAmountVoucher);
        jdbcVoucherStorage.saveVoucher(percentDiscountVoucher);

        //when
        List<Voucher> voucherList = jdbcVoucherStorage.findAllVoucher();

        //then
        assertThat(voucherList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("voucher 정보를 update할 수 있다.")
    void testUpdate() {
        //given
        UUID uuid = UUID.randomUUID();
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(uuid, 30);
        jdbcVoucherStorage.saveVoucher(percentDiscountVoucher);
        PercentDiscountVoucher updatePercentDiscountVoucher = new PercentDiscountVoucher(uuid, 60);

        //when
        Voucher updatedVoucher = jdbcVoucherStorage.update(updatePercentDiscountVoucher);

        //then
        assertThat(updatedVoucher.getVoucherDiscountValue()).isEqualTo(60);
    }

    @Test
    @DisplayName("id로 voucher를 찾을 수 있다.")
    void testFindById() {
        //given
        UUID uuid = UUID.randomUUID();
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(uuid, 50);
        jdbcVoucherStorage.saveVoucher(percentDiscountVoucher);

        //when
        Optional<Voucher> findVoucher = jdbcVoucherStorage.findById(uuid);


        //then
        assertThat(findVoucher.isEmpty()).isFalse();
        assertThat(findVoucher.get().getVoucherDiscountValue()).isEqualTo(50);
    }
}
