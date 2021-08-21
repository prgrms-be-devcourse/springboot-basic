package com.example.kdtspringmission;

import com.example.kdtspringmission.voucher.repository.MemoryVoucherRepository;
import com.example.kdtspringmission.voucher.repository.VoucherRepository;

public class AppConfig {

    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
    }

}
