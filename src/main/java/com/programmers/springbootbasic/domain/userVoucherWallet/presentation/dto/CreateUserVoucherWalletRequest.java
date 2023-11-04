package com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto;

import com.programmers.springbootbasic.domain.userVoucherWallet.domain.entity.UserVoucherWallet;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.validator.constraints.Length;

public class CreateUserVoucherWalletRequest {

    @NotNull
    @Length(min = 1, max = 10)
    private final String nickname;
    @NotNull
    private final UUID voucherId;

    public CreateUserVoucherWalletRequest(String nickname, UUID voucherId) {
        this.nickname = nickname;
        this.voucherId = voucherId;
    }

    public static CreateUserVoucherWalletRequest of(String nickname, UUID voucherId) {
        return new CreateUserVoucherWalletRequest(nickname, voucherId);
    }

    public UserVoucherWallet toEntity(Long userId, LocalDateTime createdAt) {
        return new UserVoucherWallet(userId, voucherId, createdAt);
    }

    public String getNickname() {
        return nickname;
    }

    public UUID getVoucherId() {
        return voucherId;
    }
}
