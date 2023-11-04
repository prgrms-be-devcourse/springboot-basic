package com.programmers.springbootbasic.domain.voucher.infrastructure.dto;

import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;

// TODO: 바뀔 여지가 있음
public class UserVoucherWalletWithVoucher {

    private final Long id;
    private final Voucher voucher;

    public UserVoucherWalletWithVoucher(Long id, Voucher voucher) {
        this.id = id;
        this.voucher = voucher;
    }

    public Long getId() {
        return id;
    }

    public Voucher getVoucher() {
        return voucher;
    }
}
