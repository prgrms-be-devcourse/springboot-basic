package kr.co.programmers.springbootbasic.customer.dto;

import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;

import java.util.UUID;

public class CustomerDto {
    private final UUID id;
    private final String name;
    private final CustomerStatus status;
    private final UUID walletId;

    public CustomerDto(UUID id, String name, CustomerStatus status, UUID walletId) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.walletId = walletId;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public UUID getWalletId() {
        return walletId;
    }
}
