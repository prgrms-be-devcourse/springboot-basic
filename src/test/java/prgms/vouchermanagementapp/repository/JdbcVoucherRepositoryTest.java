package prgms.vouchermanagementapp.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.value.Amount;
import prgms.vouchermanagementapp.domain.value.Ratio;
import prgms.vouchermanagementapp.service.VoucherFactory;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JdbcVoucherRepositoryTest {

    @Autowired
    private JdbcVoucherRepository jdbcVoucherRepository;

    @DisplayName("바우처를 저장할 수 있다.")
    @Test
    void save() {
        // given
        Voucher fixedAmountVoucher = VoucherFactory.createVoucher(new Amount(3000));
        Voucher percentDiscountAmountVoucher = VoucherFactory.createVoucher(new Ratio(50));

        // when
        jdbcVoucherRepository.save(fixedAmountVoucher);
        jdbcVoucherRepository.save(percentDiscountAmountVoucher);

        // then
        List<Voucher> vouchers = jdbcVoucherRepository.findAll();
        assertThat(vouchers.size()).isEqualTo(2);
    }

    @DisplayName("voucher id 를 이용하여 바우처를 조회할 수 있다.")
    @Test
    void findById() {
        // given
        Voucher savedVoucher = VoucherFactory.createVoucher(new Amount(3000));
        jdbcVoucherRepository.save(savedVoucher);

        // when
        Voucher foundVoucher = jdbcVoucherRepository.findById(savedVoucher.getVoucherId()).get();

        // then
        UUID actual = foundVoucher.getVoucherId();
        UUID expected = savedVoucher.getVoucherId();
        assertThat(actual).isEqualTo(expected);
    }
}