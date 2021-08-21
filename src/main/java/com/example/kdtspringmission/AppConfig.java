package com.example.kdtspringmission;

public class AppConfig {

    public VoucherRepository voucherRepository() {
        return new MemoryVoucherRepository();
    }

}
