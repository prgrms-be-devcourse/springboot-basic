package com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto;

import com.programmers.springbootbasic.domain.userVoucherWallet.domain.entity.UserVoucherWallet;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateUserVoucherWalletRequest {

    private final String nickname;
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
