package com.blessing333.springbasic.voucher.service;

import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherUpdateForm;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    Voucher registerVoucher(VoucherCreateForm form);

    List<Voucher> loadAllVoucher();

    List<Voucher> loadVouchersByType(Voucher.VoucherType type);

    Voucher loadVoucherById(UUID voucherId);

    void deleteVoucher(UUID voucherId);

    void updateVoucher(VoucherUpdateForm form);
}
