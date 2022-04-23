package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.domain.FixedAmountVoucher;
import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VoucherRequestTest {

    @Test
    @DisplayName("Voucher DTO가 도메인으로 변환_성공")
    void toDomain() {
        //given
        VoucherType type = VoucherType.FIXED_AMOUNT;
        int discountValue = 100;
        VoucherRequest request = new VoucherRequest(type, discountValue);
        Voucher voucher = new FixedAmountVoucher(type, discountValue);

        //then
        Voucher createdVoucher = request.toDomain();

        //when
        assertThat(createdVoucher).usingRecursiveComparison().isEqualTo(voucher);
    }
}
