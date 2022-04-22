package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class VoucherTypeTest {

    @Test
    @DisplayName("VoucherType에 따른 Voucher 도메인 생성_성공")
    void createVoucher() {
        //given
        VoucherType type = VoucherType.FIXED_AMOUNT;
        int discountValue = 100;
        Voucher voucher = new FixedAmountVoucher(type, discountValue);

        //then
        Voucher createdVoucher = type.create(discountValue);

        //when
        assertThat(createdVoucher).usingRecursiveComparison().isEqualTo(voucher);
    }
}
