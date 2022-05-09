package org.programmers.springbootbasic.web.controller.vouchers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootbasic.voucher.domain.VoucherProperty;
import org.programmers.springbootbasic.voucher.domain.VoucherType;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.ui.Model;

import java.util.Date;

import static org.mockito.Mockito.*;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.RATE;

class VoucherControllerTest {

    private static final VoucherType VOUCHER_TYPE = RATE;
    private static final Date STARTING_DATE = mock(Date.class);
    private static final Date ENDING_DATE = mock(Date.class);
    private static final Model MODEL_MOCK = mock(Model.class);

    private static final VoucherService VOUCHER_SERVICE_MOCK = mock(VoucherService.class);
    private static final VoucherProperty VOUCHER_PROPERTY_MOCK = mock(VoucherProperty.class);
    private static final VoucherController VOUCHER_CONTROLLER =
            new VoucherController(mock(VoucherRepository.class), VOUCHER_SERVICE_MOCK, VOUCHER_PROPERTY_MOCK);

    @Test
    @DisplayName("전체 조회: 아무런 인자도 들어오지 않을 경우")
    void getAllVouchers() {
        VOUCHER_CONTROLLER.voucherList(MODEL_MOCK, null, null, null);
        verify(VOUCHER_SERVICE_MOCK, times(1)).getAllVouchers();
    }

    @Test
    @DisplayName("전체 조회: 타입만 들어올 경우")
    void getAllVouchersByType() {
        VOUCHER_CONTROLLER.voucherList(MODEL_MOCK, VOUCHER_TYPE, null, null);
        verify(VOUCHER_SERVICE_MOCK, times(1)).getVouchersByType(VOUCHER_TYPE);
    }

    @Test
    @DisplayName("전체 조회: 시간만 들어올 경우")
    void getAllVouchersByDate() {
        VOUCHER_CONTROLLER.voucherList(MODEL_MOCK, null, STARTING_DATE, ENDING_DATE);
        verify(VOUCHER_SERVICE_MOCK, times(1)).getVouchersByDate(STARTING_DATE, ENDING_DATE);
    }

    @Test
    @DisplayName("전체 조회: 타입과 시간 모두 들어올 경우")
    void getAllVouchersByTypeAndDate() {
        VOUCHER_CONTROLLER.voucherList(MODEL_MOCK, VOUCHER_TYPE, STARTING_DATE, ENDING_DATE);
        verify(VOUCHER_SERVICE_MOCK, times(1)).getVouchersByTypeAndDate(VOUCHER_TYPE, STARTING_DATE, ENDING_DATE);
    }
}