package me.programmers.springboot.basic.springbootbasic.voucher.service;

import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.repository.JdbcTemplateVoucherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JdbcVoucherServiceTest {

    @InjectMocks
    JdbcVoucherService voucherService;

    @Mock
    JdbcTemplateVoucherRepository voucherRepository;

    @Test
    void saveVoucherTest() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 2000);
        given(voucherRepository.save(any(Voucher.class))).willReturn(voucher);

        Voucher savedVoucher = voucherService.save(voucher);

        assertThat(savedVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    void getAllVouchersTest() {
        Voucher fixVoucher = new FixedAmountVoucher(UUID.randomUUID(), 2000);
        Voucher percentVoucher = new PercentAmountVoucher(UUID.randomUUID(), 20);
        List<Voucher> vouchers = List.of(fixVoucher, percentVoucher);

        given(voucherRepository.findAll()).willReturn(vouchers);

        List<Voucher> allVouchers = voucherService.getAllVouchers();

        assertAll(
                () -> assertThat(allVouchers).hasSize(2),
                () -> assertThat(allVouchers.get(0).getVoucherId()).isEqualTo(fixVoucher.getVoucherId())
        );
    }

}