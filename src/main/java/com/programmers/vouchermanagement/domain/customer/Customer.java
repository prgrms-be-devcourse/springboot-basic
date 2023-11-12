package com.programmers.vouchermanagement.domain.customer;

import com.programmers.vouchermanagement.dto.CustomerDto;
import com.programmers.vouchermanagement.utils.CsvConvertable;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer implements CsvConvertable {
    private final UUID id;
    private final String name;
    private final LocalDateTime createdAt;
    private boolean isBanned;

    public Customer(CustomerDto.CreateRequest customerDto) {
        this.id = UUID.randomUUID();
        this.name = customerDto.name();
        this.createdAt = LocalDateTime.now();
        this.isBanned = false;
    }

    public Customer(String[] customerInfo) {
        this.id = UUID.fromString(customerInfo[0]);
        this.name = customerInfo[1];
        this.createdAt = LocalDateTime.parse(customerInfo[2]);
        this.isBanned = Boolean.parseBoolean(customerInfo[3]);
    }

    public Customer(UUID id, String name, LocalDateTime createdAt, boolean isBanned) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.isBanned = isBanned;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void updateToBan() {
        this.isBanned = true;
    }

    public void updateToUnban() {
        this.isBanned = false;
    }

    public String getDetailInfo() {
        return System.lineSeparator() +
                "############# Customer #############" + System.lineSeparator() +
                "Customer Id    : " + id + System.lineSeparator() +
                "Customer Name  : " + name + System.lineSeparator() +
                "Created At     : " + createdAt + System.lineSeparator() +
                "is Banned      : " + isBanned + System.lineSeparator();
    }

    @Override
    public String joinInfo(String separator) {
        return String.join(separator, id.toString(), name, createdAt.toString(), String.valueOf(isBanned));
    }
}
