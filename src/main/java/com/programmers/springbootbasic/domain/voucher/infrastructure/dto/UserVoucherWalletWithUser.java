package com.programmers.springbootbasic.domain.voucher.infrastructure.dto;

import com.programmers.springbootbasic.domain.user.domain.entity.User;

public class UserVoucherWalletWithUser {

    private final Long id;
    private final User user;

    public UserVoucherWalletWithUser(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
