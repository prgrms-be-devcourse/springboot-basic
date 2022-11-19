package org.prgrms.kdtspringdemo.domain.blacklist.model;

import org.prgrms.kdtspringdemo.io.file.CsvDto;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.UUID;

public class BlackCustomer {
    @NonNull
    private final UUID customerId;
    @NonNull
    private final String email;
    @NonNull
    private final LocalDate birth;

    public BlackCustomer(UUID customerId, String email, LocalDate birth) {
        this.customerId = customerId;
        this.email = email;
        this.birth = birth;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public CsvDto makeCsvDto() {
        return CsvDto.from(this);
    }
}
