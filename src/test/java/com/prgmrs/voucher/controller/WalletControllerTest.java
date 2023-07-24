package com.prgmrs.voucher.controller;

import com.prgmrs.voucher.controller.console.WalletController;
import com.prgmrs.voucher.controller.console.wrapper.ResponseDTO;
import com.prgmrs.voucher.dto.request.AssignVoucherRequest;
import com.prgmrs.voucher.dto.request.RemoveVoucherRequest;
import com.prgmrs.voucher.dto.response.WalletResponse;
import com.prgmrs.voucher.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@DisplayName("지갑 컨트롤러 레이어를 테스트한다.")
class WalletControllerTest {

    @InjectMocks
    WalletController walletController;

    @Mock
    WalletService walletService;

    private AssignVoucherRequest assignVoucherRequest;
    private RemoveVoucherRequest removeVoucherRequest;
    private WalletResponse walletResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        String username = "tyler";

        UUID voucherUuid = UUID.randomUUID();
        String voucherUuidString = voucherUuid.toString();

        assignVoucherRequest = new AssignVoucherRequest(username, voucherUuidString);
        removeVoucherRequest = new RemoveVoucherRequest(voucherUuidString);

        walletResponse = new WalletResponse(voucherUuid, username);
    }

    @Test
    @DisplayName("바우처 할당을 테스트한다.")
    void AssignVoucher_WalletRequest_SameWalletResponse() {
        // Given
        given(walletService.assignVoucher(assignVoucherRequest)).willReturn(walletResponse);

        // When
        ResponseDTO<?> responseDTO = walletController.assignVoucher(assignVoucherRequest);

        // Then
        assertEquals(walletResponse, responseDTO.getData());
    }

    @Test
    @DisplayName("할당된 바우처의 해제를 테스트한다.")
    void RemoveVoucher_WalletRequest_SameWalletResponse() {
        // Given
        given(walletService.removeVoucher(removeVoucherRequest)).willReturn(walletResponse);

        // When
        ResponseDTO<?> responseDTO = walletController.removeVoucher(removeVoucherRequest);
        // Then
        assertEquals(walletResponse, responseDTO.getData());
    }
}