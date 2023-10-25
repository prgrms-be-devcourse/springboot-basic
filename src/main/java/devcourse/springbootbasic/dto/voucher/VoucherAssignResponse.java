package devcourse.springbootbasic.dto.voucher;

import devcourse.springbootbasic.domain.voucher.Voucher;
import lombok.Getter;

import java.util.UUID;

@Getter
public class VoucherAssignResponse {

    private final UUID voucherId;
    private final UUID customerId;

    public VoucherAssignResponse(Voucher voucher) {
        this.voucherId = voucher.getId();
        this.customerId = voucher.getCustomerId();
    }
}
