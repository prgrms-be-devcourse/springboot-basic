package org.prgrms.deukyun.voucherapp.util.initdata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class InitData {

    private final CustomerData customerData;
    private final VoucherData voucherData;

    @PostConstruct
    void initData() {
        customerData.initData();
        voucherData.initData();
    }
}
