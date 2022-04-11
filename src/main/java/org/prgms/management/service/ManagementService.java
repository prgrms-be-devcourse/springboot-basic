package org.prgms.management.service;

import org.springframework.stereotype.Component;

@Component
public interface ManagementService {
    void run();

    void createVoucher();

    void getVoucherList();

    void getBlackList();
}
