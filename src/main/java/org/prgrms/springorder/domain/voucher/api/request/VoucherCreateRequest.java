package org.prgrms.springorder.domain.voucher.api.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.console.io.Request;

@Getter
@AllArgsConstructor
public class VoucherCreateRequest implements Request {

    private final VoucherType voucherType;

    private final long discountAmount;

    public static VoucherCreateRequest of(VoucherType voucherType, long discountAmount) {
        return new VoucherCreateRequest(voucherType, discountAmount);
    }

}
