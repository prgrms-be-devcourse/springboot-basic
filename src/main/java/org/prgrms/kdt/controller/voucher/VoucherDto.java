package org.prgrms.kdt.controller.voucher;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherType;

import static org.springframework.beans.BeanUtils.copyProperties;

public class VoucherDto {
    private Long voucherId;
    private VoucherType voucherType;
    private long amount;
    //TODO: Customer vs customerId
    private Long customerId;


    public VoucherDto(Voucher source) {
        copyProperties(source, this);
        this.voucherType = source.getType();
        this.customerId = source.getCustomerId();
    }

    public Long getVoucherId() {
        return voucherId;
    }

    public long getAmount() {
        return amount;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}
