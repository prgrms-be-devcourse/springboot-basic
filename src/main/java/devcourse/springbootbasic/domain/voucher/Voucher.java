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
    private long discountValue;
    @Builder.Default
    private UUID customerId = null;

    public Voucher(UUID id, VoucherType voucherType, long discountValue, UUID customerId) {
        this.validateDiscountValue(voucherType, discountValue);
        this.id = id;
        this.voucherType = voucherType;
        this.discountValue = discountValue;
        this.customerId = customerId;
    }

    public static Voucher createVoucher(UUID uuid, VoucherType voucherType, long discountValue, UUID customerId) {
        return Voucher.builder()
                .id(uuid)
                .voucherType(voucherType)
                .discountValue(discountValue)
                .customerId(customerId)
                .build();
    }

    private void validateDiscountValue(VoucherType voucherType, long discountValue) {
        if (!voucherType.validateDiscountValue(discountValue)) {
            throw VoucherException.of(VoucherErrorMessage.INVALID_DISCOUNT_VALUE);
        }
    }

    public Voucher updateDiscountValue(long discountValue) {
        this.validateDiscountValue(this.voucherType, discountValue);
        this.discountValue = discountValue;

        return this;
    }

    public Voucher assignToCustomer(UUID customerId) {
        this.customerId = customerId;
        return this;
    }

    public Voucher unassignToCustomer() {
        this.customerId = null;
        return this;
    }

    public boolean isAssigned() {
        return this.customerId != null;
    }
}
