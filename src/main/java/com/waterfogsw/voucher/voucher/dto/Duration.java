package com.waterfogsw.voucher.voucher.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public record Duration(
        LocalDate fromDate,
        LocalDate toDate
) {
    public Duration(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                    @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        if (fromDate != null && toDate != null) {
            validate(fromDate, toDate);
        }
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    private void validate(LocalDate fromDate, LocalDate toDate) {
        if (Period.between(fromDate, toDate).getDays() < 0) {
            throw new IllegalArgumentException("Invalid Period Input");
        }
    }

    public boolean isNull() {
        return this.fromDate == null && this.toDate == null;
    }

    public boolean isBetween(LocalDateTime localDateTime) {
        final var localDate = localDateTime.toLocalDate();
        if (this.fromDate == null && this.toDate == null) {
            return true;
        }

        if (this.fromDate == null) {
            return localDate.isAfter(this.toDate);
        }

        if (this.toDate == null) {
            return !(localDate.isBefore(this.fromDate));
        }

        return !(localDate.isBefore(this.fromDate)) || localDate.isAfter(this.toDate);
    }
}
