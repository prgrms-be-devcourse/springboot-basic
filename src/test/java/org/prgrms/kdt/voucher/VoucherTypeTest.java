package org.prgrms.kdt.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Created by yhh1056
 * Date: 2021/08/25 Time: 2:26 오후
 */
class VoucherTypeTest {

    @Test
    @DisplayName("FixedAmountVoucher 생성 테스트")
    void create_FixedAmountVoucher() {
        var voucherData = new VoucherData("1", 1000L);

        var voucherType = VoucherType.findByNumber(voucherData.getVoucherNumber());
        var voucher = voucherType.create(voucherData);

        assertThat(voucher).isInstanceOf(FixedAmountVoucher.class);
    }

    @Test
    @DisplayName("PercentDiscountVoucher 생성 테스트")
    void create_PercentDiscountVoucher() {
        var voucherData = new VoucherData("2", 30L);

        var voucherType = VoucherType.findByNumber(voucherData.getVoucherNumber());
        var voucher = voucherType.create(voucherData);

        assertThat(voucher).isInstanceOf(PercentDiscountVoucher.class);
    }

}