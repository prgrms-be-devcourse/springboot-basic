package com.prgrms.voucher_manage.domain.voucher.service;

import com.prgrms.voucher_manage.domain.voucher.dto.CreateVoucherDto;

public interface VoucherService {
    void createVoucher(CreateVoucherDto dto);

    void getVouchers();
}
