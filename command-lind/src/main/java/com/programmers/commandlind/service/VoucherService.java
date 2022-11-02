package com.programmers.commandlind.service;

import com.programmers.commandlind.entity.Voucher;
import com.programmers.commandlind.repository.VoucherRespository;

import java.text.MessageFormat;
import java.util.UUID;

public class VoucherService {

    private final VoucherRespository voucherRespository;

    public VoucherService(VoucherRespository voucherRespository) {
        this.voucherRespository = voucherRespository;
    }
    public Voucher getVoucher(UUID voucherId) {
        return voucherRespository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for ", voucherId)));
    }

    public void useVoucher(Voucher voucher) {
    }
}
