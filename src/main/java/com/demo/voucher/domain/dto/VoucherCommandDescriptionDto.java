package com.demo.voucher.domain.dto;

import lombok.Data;

@Data
public class VoucherCommandDescriptionDto {
    private final String command;
    private final String description;

    public VoucherCommandDescriptionDto(String command, String voucherDescription) {
        this.command = command;
        this.description = voucherDescription;
    }

    @Override
    public String toString() {
        return command + ": " + description;
    }
}
