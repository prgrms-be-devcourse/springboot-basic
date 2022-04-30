package org.prgrms.deukyun.voucherapp.system.initdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class InitialDataConstructorTest {

    InitDataConstructor dataConstructor;
    CustomerData customerData;
    VoucherData voucherData;

    @BeforeEach
    void setUp() {
        customerData = mock(CustomerData.class);
        voucherData = mock(VoucherData.class);
        dataConstructor = new InitDataConstructor(customerData, voucherData);
    }

    @Test
    void 성공_초기데이터_생성() {
        //when
        dataConstructor.initData();

        //then
        verify(customerData).initData();
        verify(voucherData).initData();
    }
}