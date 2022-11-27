package com.programmers.commandline.domain.consumer.dto;

import com.programmers.commandline.domain.consumer.entity.Consumer;

public class ConsumerFileInsertResponseDto {
    String id;
    String name;
    String email;
    String createdAt;
    String lastLoginAt;

    public ConsumerFileInsertResponseDto(Consumer consumer) {
        this.id = consumer.getId();
        this.name = consumer.getName();
        this.email = consumer.getEmail();
        this.createdAt = consumer.getCreatedAt().toString();
        this.lastLoginAt = consumer.getLastLoginAt().toString();
    }
}
