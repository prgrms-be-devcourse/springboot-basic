package org.prgms.management.service;

import org.springframework.stereotype.Component;

@Component
public interface VoucherService {
    void run();

    void createVoucher();

    void getVoucherList();
}
