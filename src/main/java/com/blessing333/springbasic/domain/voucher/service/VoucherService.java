package com.blessing333.springbasic.domain.voucher.service;

import com.blessing333.springbasic.domain.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.domain.voucher.dto.VoucherUpdateForm;
import com.blessing333.springbasic.domain.voucher.model.Voucher;

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
