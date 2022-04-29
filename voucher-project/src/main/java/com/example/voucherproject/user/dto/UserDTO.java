package com.example.voucherproject.user.dto;

import com.example.voucherproject.user.model.UserType;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public interface UserDTO {

    @Data
    @NoArgsConstructor
    class Create {
        private UserType type;
        private String name;
    }

    @Data
    @NoArgsConstructor
    class Update {
        private UUID id;
        private UserType type;
        private String name;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Data
    @NoArgsConstructor
    class Query {
        private UserType type;
        private String from;
        private String to;
    }

    @Builder
    @Getter
    class Result {
        private UUID id;
        private UserType type;
        private String name;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
