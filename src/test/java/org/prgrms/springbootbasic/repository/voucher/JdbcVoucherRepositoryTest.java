package org.prgrms.springbootbasic.repository.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.zaxxer.hikari.HikariDataSource;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.prgrms.springbootbasic.entity.FixedAmountVoucher;
import org.prgrms.springbootbasic.entity.PercentDiscountVoucher;
import org.prgrms.springbootbasic.entity.Voucher;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

    JdbcVoucherRepository jdbcVoucherRepository =
        new JdbcVoucherRepository(new JdbcTemplate(
            DataSourceBuilder.create()
                .url("jdbc:mysql://localhost/springboot_basic")
                .username("hyuk")
                .password("hyuk1234!")
                .type(HikariDataSource.class)
                .build()));

    @BeforeAll
    void cleanup() {
        jdbcVoucherRepository.removeAll();
    }

    @DisplayName("모든 바우처 조회 기능")
    @Test
    @Order(1)
    void findAll() {
        //given
        jdbcVoucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 100));
        jdbcVoucherRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 20));

        //when
        var vouchers = jdbcVoucherRepository.findAll();

        //then
        assertThat(vouchers.size()).isEqualTo(2);
    }

    @DisplayName("바우처 저장 기능")
    @Test
    @Order(2)
    void save() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        //when
        jdbcVoucherRepository.save(voucher);

        //then
        assertThat(jdbcVoucherRepository.findById(voucher.getVoucherId()))
            .isNotEmpty();
    }
}
