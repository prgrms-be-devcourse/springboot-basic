package com.blessing333.springbasic.voucher.service;

import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;

import java.util.List;

public interface VoucherService {
    Voucher createNewVoucher(ConvertedVoucherCreateForm form);
    List<Voucher> loadAllVoucher();
}
