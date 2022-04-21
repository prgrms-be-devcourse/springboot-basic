package com.pppp0722.vouchermanagement.entity.member;

import java.util.Optional;
import java.util.UUID;

public class Member {

    private final UUID memberId;
    private final String name;
    private final Optional<UUID> walletId = Optional.empty();

    public Member(UUID memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public Optional<UUID> getWalletId() {
        return walletId;
    }
}
