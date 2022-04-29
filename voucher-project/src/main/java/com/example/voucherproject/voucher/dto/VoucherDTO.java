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

    }

    @Data
    @NoArgsConstructor
    class Query {
        private VoucherType type;
        private String from;
        private String to;

        public LocalDateTime convertTimeFrom(){
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            try{
                return LocalDateTime.parse(from + "T00:00:00.000", formatter);
            }
            catch (Exception e){
                return LocalDateTime.MIN.truncatedTo(ChronoUnit.MILLIS);
            }
        }
        public LocalDateTime convertTimeTo(){
            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            try{
                return LocalDateTime.parse(to + "T00:00:00.000", formatter);
            }
            catch (Exception e){
                return LocalDateTime.MAX.truncatedTo(ChronoUnit.MILLIS);
            }
        }
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
