package com.programmers.voucher.domain.voucher.controller;

import com.programmers.voucher.domain.voucher.service.VoucherService;
import com.programmers.voucher.global.io.Console;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class VoucherControllerTest {

    @InjectMocks
    private VoucherController voucherController;

    @Mock
    private Console console;

    @Mock
    private VoucherService voucherService;

    @Test
    void createVoucher() {
    }

    @Test
    void findVouchers() {
    }

    @Test
    @DisplayName("성공: voucher 단건 삭제 요청")
    void deleteVoucher() {
        //given
        given(console.inputUUID()).willReturn(UUID.randomUUID());

        //when
        voucherController.deleteVoucher();

        //then
        then(voucherService).should().deleteVoucher(any());
        then(console).should().print(any());
    }
}