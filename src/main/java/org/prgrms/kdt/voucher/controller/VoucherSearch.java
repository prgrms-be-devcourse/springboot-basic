package org.prgrms.kdt.voucher.controller;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class VoucherSearch {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate from;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate to;
    private String voucherType;

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }
}
