package org.prgrms.kdtspringdemo.voucher.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.voucher.domain.FixedDiscountPolicy;
import org.prgrms.kdtspringdemo.voucher.domain.PercentDiscountPolicy;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@ActiveProfiles("DB")
class JdbcVoucherRepositoryTest {
    @Configuration
    @ComponentScan()
    static class Config {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost/kdt")
                    .username("test")
                    .password("test1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }
    @Autowired
    JdbcVoucherRepository jdbcVoucherRepository;

    @BeforeEach
    void init() {
        jdbcVoucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처를 추가합니다.")
    void insert() {
        //given
        VoucherPolicy voucherPolicy = new FixedDiscountPolicy(100L);
        Voucher voucher = new Voucher(UUID.randomUUID(), voucherPolicy);

        //when
        Voucher insertedVoucher = jdbcVoucherRepository.insert(voucher);

        //then
        assertThat(insertedVoucher.getVoucherId(), is(voucher.getVoucherId()));
        assertThat(insertedVoucher.getVoucherPolicy().getClass(), is(voucher.getVoucherPolicy().getClass()));
    }

    @Test
    @DisplayName("voucherId로 바우처를 검색합니다.")
    void findById() {
        //given
        VoucherPolicy voucherPolicy = new PercentDiscountPolicy(50L);
        Voucher voucher = new Voucher(UUID.randomUUID(), voucherPolicy);
        jdbcVoucherRepository.insert(voucher);

        //when
        Voucher findVoucher = jdbcVoucherRepository.findById(voucher.getVoucherId()).get();

        //then
        assertThat(findVoucher.getVoucherId(), is(voucher.getVoucherId()));
        assertThat(findVoucher.getVoucherPolicy().getClass(), is(voucher.getVoucherPolicy().getClass()));
    }

    @Test
    @DisplayName("모든 바우처 정보를 삭제합니다.")
    void deleteAll() {
        //given
        VoucherPolicy voucherPolicy = new PercentDiscountPolicy(50L);
        Voucher voucher = new Voucher(UUID.randomUUID(), voucherPolicy);
        jdbcVoucherRepository.insert(voucher);

        //when
        jdbcVoucherRepository.deleteAll();
        List<Voucher> voucherList = jdbcVoucherRepository.findAll().get();

        //then
        assertThat(voucherList.size(), is(0));
    }

    @Test
    @DisplayName("모든 바우처를 조회합니다.")
    void findAll() {
        //given
        VoucherPolicy percentDiscountPolicy = new PercentDiscountPolicy(50L);
        VoucherPolicy fixedDiscountPolicy = new FixedDiscountPolicy(4000L);
        Voucher voucher1 = new Voucher(UUID.randomUUID(), percentDiscountPolicy);
        Voucher voucher2 = new Voucher(UUID.randomUUID(), fixedDiscountPolicy);
        jdbcVoucherRepository.insert(voucher1);
        jdbcVoucherRepository.insert(voucher2);

        //when
        List<Voucher> voucherList = jdbcVoucherRepository.findAll().get();

        //then
        assertThat(voucherList.size(), is(2));
    }
}