package org.prgrms.deukyun.voucherapp.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.deukyun.voucherapp.domain.customer.service.CustomerService;
import org.prgrms.deukyun.voucherapp.util.initdata.CustomerData;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class InitialDataConstructorTest {

    CustomerData dataConstructor;
    CustomerService mockCustomerService;

    static int AMOUNT_INIT_CUSTOMER = 100;
    static int AMOUNT_INIT_OWNED_VOUCHERS = 400;
    static int AMOUNT_INIT_NOT_OWNED_VOUCHERS = 100;

    @BeforeEach
    void setUp() {
        mockCustomerService = mock(CustomerService.class);
        dataConstructor = new CustomerData(mockCustomerService);
    }

    @Test
    void 성공_초기데이터_생성() {
        //when
        dataConstructor.initData();

        //then
        verify(mockCustomerService, times(AMOUNT_INIT_CUSTOMER)).insert(any());
//        verify(mockVoucherService, times(AMOUNT_INIT_OWNED_VOUCHERS)).insert(ownedVoucher());
//        verify(mockVoucherService, times(AMOUNT_INIT_NOT_OWNED_VOUCHERS)).insert(notOwnedVoucher());
    }



//    private Voucher notOwnedVoucher() {
//        return argThat(voucher -> voucher.getCustomerId().isEmpty());
//    }
//
//    private  Voucher ownedVoucher() {
//        return argThat(voucher -> voucher.getCustomerId().isPresent());
//    }

}