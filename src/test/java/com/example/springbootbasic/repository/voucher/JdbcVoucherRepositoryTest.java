package com.example.springbootbasic.repository.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherFactory;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.util.List;

import static com.example.springbootbasic.domain.voucher.VoucherType.FIXED_AMOUNT;
import static com.example.springbootbasic.domain.voucher.VoucherType.PERCENT_DISCOUNT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringJUnitConfig
@ActiveProfiles("dev")
class JdbcVoucherRepositoryTest {

    @Configuration
    static class Config {
        @Bean
        DataSource dataSource() {
            return DataSourceBuilder.create()
                    .url("jdbc:mysql://localhost:3306/spb_basic")
                    .username("root")
                    .password("root1234!")
                    .type(HikariDataSource.class)
                    .build();
        }

        @Bean
        JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }

        @Bean
        NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
            return new NamedParameterJdbcTemplate(jdbcTemplate());
        }

        @Bean
        JdbcVoucherRepository voucherRepository() {
            return new JdbcVoucherRepository(namedParameterJdbcTemplate());
        }
    }

    @Autowired
    private JdbcVoucherRepository voucherRepository;


    @BeforeEach
    void beforeEach() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("모든 타입의 바우처 검색 성공")
    void whenFindAllVouchersThenSuccessTest() {
        // given
        Voucher voucher1 = VoucherFactory.of(1L, FIXED_AMOUNT);
        Voucher voucher2 = VoucherFactory.of(100L, FIXED_AMOUNT);
        Voucher voucher3 = VoucherFactory.of(1L, PERCENT_DISCOUNT);
        Voucher voucher4 = VoucherFactory.of(10L, PERCENT_DISCOUNT);

        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
        voucherRepository.save(voucher3);
        voucherRepository.save(voucher4);

        // when
        List<Voucher> allVouchers = voucherRepository.findAllVouchers();
        System.out.println("allVouchers = " + allVouchers);

        // then
        assertThat(allVouchers, hasSize(allVouchers.size()));
    }
}