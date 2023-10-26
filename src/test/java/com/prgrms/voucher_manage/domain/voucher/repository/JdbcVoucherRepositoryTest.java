package com.prgrms.voucher_manage.domain.voucher.repository;

import com.prgrms.voucher_manage.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.FIXED;
import static org.assertj.core.api.Assertions.assertThat;
@JdbcTest
@ActiveProfiles({"prod","test"})
@Import(JdbcVoucherRepository.class)
public class JdbcVoucherRepositoryTest {
    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;


    @Test
    @DisplayName("voucher를 저장할 수 있다.")
    public void jdbcVoucherRepository_save() {
        //given
        FixedAmountVoucher voucher = new FixedAmountVoucher(1000L);
        //when
        Voucher savedVoucher = jdbcVoucherRepository.save(voucher);
        //then
        assertThat(voucher).isNotNull();
        assertThat(voucher.getDiscountAmount()).isEqualTo(1000L);
        assertThat(savedVoucher.getType()).isEqualTo(FIXED);
    }

    @Test
    @DisplayName("voucher를 찾을 수 있다.")
    public void jdbcVoucherRepository_find(){
        //given
        List<Voucher> all = jdbcVoucherRepository.findAll();
        assertThat(all.get(0).getDiscountAmount()).isEqualTo(1000L);
    }
}
