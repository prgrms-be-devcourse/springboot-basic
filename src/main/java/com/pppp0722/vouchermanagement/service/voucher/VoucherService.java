package com.pppp0722.vouchermanagement.service.voucher;

import com.pppp0722.vouchermanagement.entity.voucher.Voucher;
import com.pppp0722.vouchermanagement.entity.voucher.VoucherType;
import java.util.List;
import java.util.UUID;

public interface VoucherService {

    void createVoucher(VoucherType voucherType, UUID voucherId, long amount);

    List<Voucher> getAllVouchers();
}
