package org.prgrms.kdt.domain.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class VoucherForm {
    private Long voucherId;
    private String voucherType;
    private Long amount;
    private boolean status;

    public Long getVoucherId() {
        return voucherId;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public Long getAmount() {
        return amount;
    }

    public boolean getStatus() {
        return status;
    }
}
