package com.prgms.springbootbasic.persistent;

import com.prgms.springbootbasic.domain.FixedAmountVoucher;
import com.prgms.springbootbasic.domain.PercentAmountVoucher;
import com.prgms.springbootbasic.domain.Voucher;
import com.prgms.springbootbasic.util.YamlPropertyFactory;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringJUnitConfig
class VouchersInDBTest {

    @Configuration
    @ComponentScan(basePackages = "com.prgms.springbootbasic")
    @PropertySource(value = "application.yaml", factory = YamlPropertyFactory.class)
    static class TestConfig {

        @Bean
        public DataSource dataSource(@Value("${database.url}") String url,
                                     @Value("${database.hostname}") String hostname,
                                     @Value("${database.password}") String password) {
            HikariDataSource datasource = DataSourceBuilder.create()
                    .url(url)
                    .username(hostname)
                    .password(password)
                    .type(HikariDataSource.class)
                    .build();
            return datasource;
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
    private VouchersInDB vouchersInDB;

    @Test
    @DisplayName("바우처가 저장된다.")
    void 바우처_저장() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        vouchersInDB.save(voucher);
        Voucher findVoucher = vouchersInDB.findById(voucher.getVoucherId());
        // 필드 이름 모두 같은지 확인
        assertThat(findVoucher).usingRecursiveComparison()
                .isEqualTo(voucher);
    }

    @Test
    @DisplayName("동일한 아이디의 바우처 저장 시 에러가 발생한다.")
    void 동일한_아이디의_바우처_저장_시_에러() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);

        // when
        vouchersInDB.save(voucher);

        // then
        assertThatThrownBy(() -> {
            vouchersInDB.save(voucher);
        }).isExactlyInstanceOf(DuplicateKeyException.class);
    }

    @Test
    @DisplayName("바우처를 모두 찾는다.")
    void 바우처를_모두_찾는다() {
        // given
        Voucher fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        Voucher percentVoucher = new PercentAmountVoucher(UUID.randomUUID(), 50);
        vouchersInDB.save(fixedVoucher);
        vouchersInDB.save(percentVoucher);

        // when
        List<Voucher> vouchers = vouchersInDB.findAll();

        // then
        assertThat(vouchers).extracting(Voucher::getVoucherId)
                .contains(fixedVoucher.getVoucherId(), percentVoucher.getVoucherId());
    }

    @Test
    @DisplayName("바우처의 아이디로 특정 바우처를 찾는다.")
    void 바우처의_아이디로_특정_바우처를_찾는다() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        vouchersInDB.save(voucher);

        // when
        Voucher findVoucher = vouchersInDB.findById(voucher.getVoucherId());

        // then
        assertThat(findVoucher).usingRecursiveComparison()
                .isEqualTo(voucher);
    }

    @Test
    @DisplayName("해당하는 아이디의 바우처가 없다면 에러를 던진다.")
    void 해당하는_아이디의_바우처가_없다면_에러() {
        // when
        assertThatThrownBy(() -> {
            vouchersInDB.findById(UUID.randomUUID());
        }).isExactlyInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("바우처의 할인가를 수정한다")
    void 바우처의_할인가를_수정한다() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        vouchersInDB.save(voucher);
        voucher.changeAmount(2000L);

        // when
        vouchersInDB.update(voucher);
        Voucher modifiedVoucher = vouchersInDB.findById(voucher.getVoucherId());

        // then
        assertThat(modifiedVoucher).usingRecursiveComparison()
                .isEqualTo(voucher);
    }

    @Test
    @DisplayName("바우처의 아이디로 특정 바우처를 삭제한다.")
    void 바우처의_아이디로_특정_바우처를_삭제한다() {
        // given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        vouchersInDB.save(voucher);

        // when
        vouchersInDB.deleteOne(voucher.getVoucherId());

        // then
        assertThatThrownBy(() -> {
            vouchersInDB.findById(voucher.getVoucherId());
        }).isExactlyInstanceOf(EmptyResultDataAccessException.class);
    }

}