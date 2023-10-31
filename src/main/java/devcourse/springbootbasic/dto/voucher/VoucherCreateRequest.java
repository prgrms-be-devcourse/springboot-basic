package devcourse.springbootbasic.dto.voucher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;

import java.util.UUID;

public class VoucherCreateRequest {

    private final VoucherType voucherType;
    private final long discountValue;

    @JsonCreator
    public VoucherCreateRequest(@JsonProperty("voucherType") VoucherType voucherType,
                                @JsonProperty("discountValue") long discountValue) {
        this.voucherType = voucherType;
        this.discountValue = discountValue;
    }

    public Voucher toEntity(UUID voucherId) {
        return Voucher.builder()
                .id(voucherId)
                .voucherType(voucherType)
                .discountValue(discountValue)
                .build();
    }
}
