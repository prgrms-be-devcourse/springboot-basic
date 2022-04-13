package com.blessing333.springbasic.component.voucher.service;

import com.blessing333.springbasic.component.voucher.dto.ConvertedVoucherCreateForm;

public interface VoucherService {
    void createNewVoucher(ConvertedVoucherCreateForm form);
    void showVoucherList();
}
