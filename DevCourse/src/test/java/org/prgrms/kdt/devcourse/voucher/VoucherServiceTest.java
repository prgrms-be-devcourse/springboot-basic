package org.prgrms.kdt.devcourse.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    @Test
    @DisplayName("바우처 ID로 바우처를 가져올 수 있다.")
    void getVoucher() {
        //GIVEN
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000L);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);
        VoucherRepository voucherRepository = new MemoryVoucherRepository();
        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        //WHEN
        var sut = new VoucherService(voucherRepository);
        var foundedVoucher = sut.getVoucher(fixedAmountVoucher.getVoucherId());

        //THEN
        assertThat(foundedVoucher,samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    void useVoucher() {
    }

    @Test
    @DisplayName("바우처를 생성할 수 있다.")
    void createVoucher() {
        //GIVEN
        VoucherRepository voucherRepository = new MemoryVoucherRepository();

        //WHEN
        var sut = new VoucherService(voucherRepository);

        var createdVoucher = sut.createVoucher(VoucherType.PERCENT, 10L);

        //THEN
        assertThat(createdVoucher.getVoucherType(),is(VoucherType.PERCENT));
        assertThat(createdVoucher.getVoucherAmount(), is(10L));

    }

    @Test
    @DisplayName("모든 바우처를 조회할 수 있다.")
    void getAllVouchers() {
        //테스트 짤때 같은 클래스안의 메소드를 사용하는게 좋을지? 그냥

        //GIVEN : 바우처 레포에 바우처 하나가 미리 insert
        VoucherRepository voucherRepository = new MemoryVoucherRepository();

        //WHEN
        var sut = new VoucherService(voucherRepository);

        sut.createVoucher(VoucherType.FIXED, 1000L);
        sut.createVoucher(VoucherType.PERCENT, 10L);

        var voucherList = sut.getAllVouchers();

        //THEN
        assertThat(voucherList ,hasSize(2));

    }
}