package org.prgrms.deukyun.voucherapp.system.initdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.customer.service.CustomerService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerDataTest {

    CustomerData customerData;
    CustomerService customerService;

    static int AMOUNT_INIT_CUSTOMER = 100;

    @BeforeEach
    void setUp() {
        customerService = mock(CustomerService.class);
        customerData = new CustomerData(customerService);
    }

    @Test
    void 성공_고객_데이터_초기화() {
        //when
        customerData.initData();

        //then
        verify(customerService, times(AMOUNT_INIT_CUSTOMER)).insert(any());
    }
}