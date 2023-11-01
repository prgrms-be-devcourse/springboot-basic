package com.programmers.springbootbasic.domain.userVoucherWallet.presentation.dto;

import com.programmers.springbootbasic.domain.voucher.domain.entity.Voucher;
import com.programmers.springbootbasic.domain.voucher.presentation.dto.VoucherResponse;

public class UserOwnedVoucherResponse {

    private final Long id;
    private final VoucherResponse voucherResponse;

    public UserOwnedVoucherResponse(Long id, VoucherResponse voucherResponse) {
        this.id = id;
        this.voucherResponse = voucherResponse;
    }

    public static UserOwnedVoucherResponse of(Long id, Voucher voucher) {
        return new UserOwnedVoucherResponse(id, VoucherResponse.of(voucher));
    }

    @Override
    public String toString() {
        return """
            UserVoucher id = %s
            ---Voucher Info---
            %s
            """.formatted(id, voucherResponse);
    }

    public Long getId() {
        return id;
    }

    public VoucherResponse getVoucherResponse() {
        return voucherResponse;
    }
}
