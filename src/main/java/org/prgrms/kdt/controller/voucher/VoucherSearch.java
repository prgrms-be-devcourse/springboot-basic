package org.prgrms.kdt.controller.voucher;

import org.prgrms.kdt.model.voucher.VoucherType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;

public class VoucherSearch {

    private Optional<LocalDate> startDate;
    private Optional<LocalDate> endDate;
    private Optional<VoucherType> type;

    public VoucherSearch(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate,
        Optional<VoucherType> type
    ) {
        if (startDate.isPresent()
            && endDate.isPresent()
            && endDate.get().isBefore(startDate.get())
        ) {
            throw new IllegalArgumentException("start-date must be before than end-date");
        }

        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public Optional<LocalDate> getStartDate() {
        return startDate;
    }

    public Optional<LocalDate> getEndDate() {
        return endDate;
    }

    public Optional<VoucherType> getType() {
        return type;
    }
}
