package org.prgrms.kdt.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.prgrms.kdt.domain.voucher.VoucherSearch;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

public class RequestSearchVoucherDto {

    private String voucherType;
    @DateTimeFormat(iso = DATE)
    private LocalDate createdAt;

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType.toUpperCase();
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public VoucherSearch toVoucherSearch() {
        VoucherSearch voucherSearch = new VoucherSearch();

        if (this.voucherType != null) {
            voucherSearch.setVoucherType(VoucherType.of(this.voucherType));
        }

        if (this.createdAt != null) {
            voucherSearch.setCreatedAt(createdAt.atTime(23, 59));
        }

        return voucherSearch;
    }
}
