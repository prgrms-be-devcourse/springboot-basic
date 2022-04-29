package com.example.voucherproject.voucher.dto;

import com.example.voucherproject.voucher.model.VoucherType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.example.voucherproject.common.exception.ErrorCode.INVALID_INPUT_VALUE;

public interface VoucherDTO {

    @Data
    @NoArgsConstructor
    class Create {
        private VoucherType type;
        @PositiveOrZero @Max(10000)
        private long amount;
    }

    @Data
    @NoArgsConstructor
    class Update {
        private UUID id;
        private VoucherType type;

        @PositiveOrZero
        private long amount;
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        private LocalDateTime updatedAt;
    }

    @Data
    @NoArgsConstructor
    class Query {
        private VoucherType type;
        private String from;
        private String to;
    }

    @Builder
    @Getter
    class Result {
        @NotEmpty
        private UUID id;
        @NotEmpty
        private VoucherType type;
        @NotEmpty
        private Long amount;

        @PastOrPresent
        private LocalDateTime createdAt;

        @PastOrPresent
        private LocalDateTime updatedAt;
    }
}
