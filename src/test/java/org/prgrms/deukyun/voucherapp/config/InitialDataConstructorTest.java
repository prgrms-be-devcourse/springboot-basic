package org.prgrms.deukyun.voucherapp.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;
import org.prgrms.deukyun.voucherapp.domain.customer.service.CustomerService;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


class InitialDataConstructorTest {

    InitialDataConstructor dataConstructor;
    VoucherService mockVoucherService;
    CustomerService mockCustomerService;

    static int AMOUNT_INIT_CUSTOMER = 100;
    static int AMOUNT_INIT_OWNED_VOUCHERS = 400;
    static int AMOUNT_INIT_NOT_OWNED_VOUCHERS = 100;

    @BeforeEach
    void setUp() {
        mockVoucherService = mock(VoucherService.class);
        mockCustomerService = mock(CustomerService.class);
        dataConstructor = new InitialDataConstructor(mockVoucherService, mockCustomerService);
    }

    @Test
    void 성공_초기데이터_생성() {
        //when
        dataConstructor.initData();

        //then
        verify(mockCustomerService, times(AMOUNT_INIT_CUSTOMER)).insert(any());
        verify(mockVoucherService, times(AMOUNT_INIT_OWNED_VOUCHERS)).insert(ownedVoucher());
        verify(mockVoucherService, times(AMOUNT_INIT_NOT_OWNED_VOUCHERS)).insert(notOwnedVoucher());
    }



    private Voucher notOwnedVoucher() {
        return argThat(voucher -> voucher.getCustomerId().isEmpty());
    }

    private  Voucher ownedVoucher() {
        return argThat(voucher -> voucher.getCustomerId().isPresent());
    }

}