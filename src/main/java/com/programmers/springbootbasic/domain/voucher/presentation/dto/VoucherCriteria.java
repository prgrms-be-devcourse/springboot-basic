package com.programmers.springbootbasic.domain.voucher.presentation.dto;

import com.programmers.springbootbasic.domain.voucher.domain.VoucherType.VoucherTypeEnum;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class VoucherCriteria {
    @PastOrPresent
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @PastOrPresent
    @Nullable
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @Nullable
    private VoucherTypeEnum type;

    public VoucherCriteria(LocalDate startDate, LocalDate endDate, VoucherTypeEnum type) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    @Nullable
    public LocalDate getStartDate() {
        return startDate;
    }

    @Nullable
    public LocalDate getEndDate() {
        if (endDate == null) {
            return null;
        }
        return endDate.plusDays(1);
    }

    @Nullable
    public VoucherTypeEnum getType() {
        return type;
    }
}
