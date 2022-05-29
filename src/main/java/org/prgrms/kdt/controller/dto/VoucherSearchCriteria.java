package org.prgrms.kdt.controller.dto;

import java.time.LocalDateTime;
import java.util.Objects;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.springframework.format.annotation.DateTimeFormat;

public class VoucherSearchCriteria {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime endDate;
    private VoucherType type;

    public VoucherSearchCriteria(LocalDateTime startDate, LocalDateTime endDate, VoucherType type) {
        if (Objects.nonNull(endDate) && Objects.nonNull(startDate) && endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("날짜 범위 선택이 잘못되었습니다.");
        }
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public VoucherType getType() {
        return type;
    }

}
