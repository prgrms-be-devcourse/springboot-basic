package org.prgrms.deukyun.voucherapp.system.initdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.prgrms.deukyun.voucherapp.domain.customer.service.CustomerService;
import org.prgrms.deukyun.voucherapp.domain.voucher.domain.Voucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;

import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.prgrms.deukyun.voucherapp.domain.testutil.Fixture.customer;

class VoucherDataTest {

    VoucherData voucherData;
    VoucherService voucherService;
    CustomerService customerService;

    private static final int AMOUNT_INIT_OWNED_VOUCHERS = 400;
    private static final int AMOUNT_INIT_NOT_OWNED_VOUCHERS = 100;

    @BeforeEach
    void setUp() {
        voucherService = Mockito.mock(VoucherService.class);
        customerService = Mockito.mock(CustomerService.class);
        voucherData = new VoucherData(voucherService, customerService);
    }

    @Test
    void 성공_바우처_데이터_초기화() {
        //given
        when(customerService.findAll()).thenReturn(List.of(customer()));

        //when
        voucherData.initData();

        //then
        verify(voucherService, times(AMOUNT_INIT_OWNED_VOUCHERS)).insert(ownedVoucher());
        verify(voucherService, times(AMOUNT_INIT_NOT_OWNED_VOUCHERS)).insert(notOwnedVoucher());
    }

    private Voucher notOwnedVoucher() {
        return argThat(voucher -> voucher.getCustomerId().isEmpty());
    }

    private Voucher ownedVoucher() {
        return argThat(voucher -> voucher.getCustomerId().isPresent());
    }
}