package com.programmers.voucher.service;

import com.programmers.voucher.VoucherFactory;
import com.programmers.voucher.domain.voucher.Voucher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.programmers.voucher.domain.voucher.VoucherList.FixedAmount;
import static com.programmers.voucher.domain.voucher.VoucherList.PercentDiscount;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class VoucherServiceImplTest {

    @Autowired
    VoucherService service;

    @Test
    void 바우처저장() {
        Voucher voucher = VoucherFactory.createVoucher(FixedAmount, 3000);
        Voucher result = service.register(voucher);

        assertThat(voucher.getVoucherId()).isEqualTo(result.getVoucherId());
        assertThat(voucher.getValue()).isEqualTo(result.getValue());
    }

    @Test
    void 바우처단건조회() {
        Voucher voucher = VoucherFactory.createVoucher(PercentDiscount, 5);
        service.register(voucher);

        Voucher findOne = service.getVoucher(voucher.getVoucherId());

        assertThat(findOne.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(findOne.getValue()).isEqualTo(voucher.getValue());
    }

    @Test
    void 모든바우처조회() {
        List<Voucher> vouchers = service.findAll();
        assertThat(vouchers.size()).isEqualTo(0);

        Voucher voucher1 = VoucherFactory.createVoucher(FixedAmount, 3000);
        Voucher voucher2 = VoucherFactory.createVoucher(PercentDiscount, 7);

        service.register(voucher1);
        service.register(voucher2);

        vouchers = service.findAll();
        assertThat(vouchers.size()).isEqualTo(2);
    }
}