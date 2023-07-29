package org.programmers.VoucherManagement.global.entity;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class BaseTimeEntity {
    protected String createdAt;

    public BaseTimeEntity() {
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
