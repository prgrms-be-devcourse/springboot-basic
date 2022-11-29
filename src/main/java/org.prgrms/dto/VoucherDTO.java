package org.prgrms.dto;

import org.springframework.lang.NonNull;

public class VoucherDTO {

    @NonNull
    private final String type;
    @NonNull
    private final long amount;

    public VoucherDTO(String type, long amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
}
