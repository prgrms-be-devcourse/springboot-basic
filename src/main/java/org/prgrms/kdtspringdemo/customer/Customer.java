package org.prgrms.kdtspringdemo.customer;

import org.prgrms.kdtspringdemo.io.file.CsvDto;

import java.time.LocalDate;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private final String name;
    private final LocalDate birth;

    public Customer(UUID customerId, String name, LocalDate dateOfBirth) {
        this.customerId = customerId;
        this.name = name;
        this.birth = dateOfBirth;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public CsvDto makeCsvDto() {
        return CsvDto.from(this);
    }
}
