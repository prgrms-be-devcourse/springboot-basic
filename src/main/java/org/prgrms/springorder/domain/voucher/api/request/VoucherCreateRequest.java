package org.prgrms.springorder.domain.voucher.api.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.console.io.Request;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherCreateRequest implements Request {

    @NotNull
    private VoucherType voucherType;

    @NotNull
    private Long discountAmount;

    public static VoucherCreateRequest of(VoucherType voucherType, long discountAmount) {
        return new VoucherCreateRequest(voucherType, discountAmount);
    }

}
