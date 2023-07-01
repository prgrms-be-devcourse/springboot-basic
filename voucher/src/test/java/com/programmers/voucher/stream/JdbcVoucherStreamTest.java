package com.programmers.voucher.stream;

import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.Voucher;
import com.programmers.voucher.stream.voucher.JdbcVoucherStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringJUnitConfig
class JdbcVoucherStreamTest {

    @Configuration
    static class Config {
        @Bean
        DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("classpath:schema.sql")
                    .build();
        }

        @Bean
        JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }

        @Bean
        NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
            return new NamedParameterJdbcTemplate(jdbcTemplate);
        }
    }

    @Autowired
    DataSource dataSource;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    JdbcVoucherStream voucherStream;

    @BeforeEach
    void initialization() {
        voucherStream = new JdbcVoucherStream(dataSource, namedParameterJdbcTemplate);
        voucherStream.deleteAll();
    }

    @Test
    @DisplayName("바우처를 저장하면 db에 저장되어야 함.")
    void save() {
        // given
        assertThat(voucherStream.findAll()).isEmpty();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher("skdodoll", 10000);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher("testId", 80);

        // when
        voucherStream.save(fixedAmountVoucher);
        voucherStream.save(percentDiscountVoucher);
        // then
        assertThat(voucherStream.findAll().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("바우처 조회 로직 검증")
    void findAll() {

        // given
        String voucherId = "skdodoll";
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 10000);
        voucherStream.save(fixedAmountVoucher);
        // when
        Map<String, Voucher> voucherMap = voucherStream.findAll();
        // then
        assertAll(
                () -> assertThat(voucherMap.keySet()).contains(voucherId),
                () -> assertThat(voucherMap.get(voucherId).getVoucherId()).isSameAs(fixedAmountVoucher.getVoucherId()),
                () -> assertThat(voucherMap.get(voucherId)).isInstanceOf(FixedAmountVoucher.class)
        );
    }

    @Test
    @DisplayName("바우처 단건 조회 로직 검증")
    void findById() {
        // given
        String voucherId = "skdodoll";
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 10000);
        voucherStream.save(fixedAmountVoucher);
        // when
        Voucher findVoucher = voucherStream.findById(voucherId);
        // then
        assertThat(findVoucher.getVoucherId()).isEqualTo(fixedAmountVoucher.getVoucherId());
    }

    @Test
    @DisplayName("업데이트 로직 검증")
    void update() {

        // given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher("test-user", 10000);
        voucherStream.save(fixedAmountVoucher);
        // when
        fixedAmountVoucher.setAmount(20000);
        voucherStream.update(fixedAmountVoucher);
        // then
        Map<String, Voucher> voucherMap = voucherStream.findAll();
        Voucher findVoucher = voucherMap.get(fixedAmountVoucher.getVoucherId());

        assertThat(((FixedAmountVoucher) findVoucher).getAmount()).isEqualTo(20000);

    }

    @Test
    @DisplayName("삭제 로직 검증")
    void deleteAll() {

        // given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher("test123", 10000);
        voucherStream.save(fixedAmountVoucher);
        assertThat(voucherStream.findAll().size()).isEqualTo(1);
        // when
        voucherStream.deleteAll();

        // then
        assertThat(voucherStream.findAll().size()).isEqualTo(0);
    }
}
