package org.prgms.voucheradmin.domain.voucher.dto;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;

import java.time.LocalDateTime;

public class VoucherCondition {
    private VoucherType voucherType;
    private LocalDateTime from;
    private LocalDateTime to;

    public VoucherCondition(VoucherType voucherType, LocalDateTime from, LocalDateTime to) {
        this.voucherType = voucherType;
        this.from = from;
        this.to = to;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}
