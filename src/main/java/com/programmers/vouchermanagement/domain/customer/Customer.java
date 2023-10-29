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

    public Customer(CustomerDto.Create customerDto) {
        if (customerDto.id == null) customerDto.id = UUID.randomUUID();
        if (customerDto.createdAt == null) customerDto.createdAt = LocalDateTime.now();
        this.id = customerDto.id;
        this.name = customerDto.name;
        this.createdAt = customerDto.createdAt;
        this.isBanned = customerDto.isBanned;
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

    @Override
    public String toString() {
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
