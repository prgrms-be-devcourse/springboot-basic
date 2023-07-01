package com.prgrms.model.voucher;

import com.prgrms.model.dto.VoucherResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoucherListTest {
    UUID voucherId1 = UUID.randomUUID();
    UUID voucherId2 = UUID.randomUUID();

    Voucher createdVoucher1 = new FixedAmountVoucher(voucherId1, new Discount(20), VoucherType.FIXED_AMOUNT_VOUCHER);
    Voucher createdVoucher2 = new PercentDiscountVoucher(voucherId2, new Discount(20), VoucherType.PERCENT_DISCOUNT_VOUCHER);

    @Test
    @DisplayName("빈 바우처를 호출하는 경우 예외를 던지는지 테스트")
    void validVoucherList() {
        List<Voucher> list = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> new VoucherList(list));
    }

    @Test
    @DisplayName("Voucher List를 VoucherResponse List로 잘 변환하는지 테스트")
    void convertVoucherResponse() {

        VoucherResponse voucherResponse1 = VoucherResponse.of(createdVoucher1);
        VoucherResponse voucherResponse2 = VoucherResponse.of(createdVoucher2);

        List<VoucherResponse> voucherResponseList = List.of(voucherResponse1, voucherResponse2);

        List<Voucher> vouchers = List.of(createdVoucher1, createdVoucher2);
        VoucherList voucherList = new VoucherList(vouchers);

        assertEquals(voucherResponseList, voucherList.convertVoucherResponse());
    }
}