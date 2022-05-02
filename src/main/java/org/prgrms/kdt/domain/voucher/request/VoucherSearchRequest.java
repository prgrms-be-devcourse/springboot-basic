package org.prgrms.kdt.domain.voucher.request;

import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class VoucherSearchRequest {
    private VoucherType voucherType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    public VoucherSearchRequest(VoucherType voucherType, LocalDate createdDate) {
        this.voucherType = voucherType;
        this.createdDate = createdDate;
    }

    public VoucherSearchRequest() {
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
