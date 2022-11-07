package com.programmers.voucher.service;

import com.programmers.voucher.voucher.VoucherFactory;
import com.programmers.voucher.voucher.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.voucher.VoucherList.FixedAmount;
import static com.programmers.voucher.voucher.VoucherList.PercentDiscount;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;


@SpringBootTest
class VoucherServiceImplTest {

    @Autowired
    VoucherService service;

    @Test
    @DisplayName("저장후 리턴하는 바우처의 Id, value값은 저장하기 전 바우처의 Id, value와 동일해야 한다.")
    void 바우처저장() {
        Voucher voucher = VoucherFactory.createVoucher(FixedAmount, 3000);
        Voucher result = service.register(voucher);

        assertThat(voucher.getVoucherId()).isEqualTo(result.getVoucherId());
        assertThat(voucher.getValue()).isEqualTo(result.getValue());
    }

    @Test
    @DisplayName("저장한 바우처의 Id로 조회할 경우, 저장한 바우처와 Id, value가 동일해야 한다.")
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

    @Test
    @DisplayName("없는 아이디로 바우처를 조회할 경우 런타임 예외를 던져야 한다.")
    void 바우처조회실패() {
        UUID saveId = UUID.randomUUID();
        VoucherFactory.createVoucher(saveId, FixedAmount, 2000);

        UUID notExistId = UUID.randomUUID();

        assertThrowsExactly(RuntimeException.class,
                () -> service.getVoucher(notExistId));

    }
}