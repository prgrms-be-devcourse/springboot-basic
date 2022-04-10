package com.blessing333.springbasic.voucher.service;

import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;

public interface VoucherService {
    void createNewVoucher(ConvertedVoucherCreateForm form);
    void showVoucherList();
}
