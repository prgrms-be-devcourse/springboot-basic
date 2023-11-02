package com.zerozae.voucher.dto.wallet;

import com.zerozae.voucher.domain.wallet.Wallet;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.UUID;

@Getter
public record WalletCreateRequest(
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "올바른 UUID 형식이어야 합니다.") String customerId,
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "올바른 UUID 형식이어야 합니다.") String voucherId) {

    public WalletCreateRequest(String customerId, String voucherId) {
        this.customerId = customerId;
        this.voucherId = voucherId;
    }

    public Wallet to() {
        return new Wallet(UUID.fromString(customerId), UUID.fromString(voucherId));
    }
}
