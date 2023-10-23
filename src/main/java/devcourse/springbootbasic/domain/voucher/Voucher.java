package devcourse.springbootbasic.domain.voucher;

import devcourse.springbootbasic.exception.VoucherErrorMessage;
import devcourse.springbootbasic.exception.VoucherException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
public class Voucher {

    private final UUID id;
    private final VoucherType voucherType;
    private final long discountValue;

    public Voucher(UUID id, VoucherType voucherType, long discountValue) {
        this.validateDiscountValue(voucherType, discountValue);
        this.id = id;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public static Voucher createVoucher(UUID uuid, VoucherType voucherType, long discountValue) {
        return Voucher.builder()
                .id(uuid)
                .voucherType(voucherType)
                .discountValue(discountValue)
                .build();
    }

    private void validateDiscountValue(VoucherType voucherType, long discountValue) {
        if (!voucherType.validateDiscountValue(discountValue)) {
            throw VoucherException.of(VoucherErrorMessage.INVALID_DISCOUNT_VALUE);
        }
    }
}
