package org.prgrms.kdt.controller.dto;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class VoucherSearchCriteria {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime endDate;
    private String type;

    public VoucherSearchCriteria(LocalDateTime startDate, LocalDateTime endDate, String type) {
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

    public String getType() {
        return type;
    }

}
