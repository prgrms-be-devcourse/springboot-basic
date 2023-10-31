package devcourse.springbootbasic.dto.voucher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class VoucherUpdateDiscountValueRequest {

    private final long discountValue;

    @JsonCreator
    public VoucherUpdateDiscountValueRequest(@JsonProperty("discountValue") long discountValue) {
        this.discountValue = discountValue;
    }
}
