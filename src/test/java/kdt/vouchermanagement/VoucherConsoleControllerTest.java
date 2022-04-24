package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.controller.VoucherConsoleController;
import kdt.vouchermanagement.domain.voucher.domain.Voucher;
import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import kdt.vouchermanagement.domain.voucher.service.VoucherService;
import kdt.vouchermanagement.global.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherConsoleControllerTest {

    @InjectMocks
    VoucherConsoleController voucherConsoleController;

    @Mock
    VoucherService voucherService;

    @Test
    @DisplayName("전달받은 DTO의 VoucherType이 유효하지 않으면_실패")
    void invalidVoucherType() {
        //given
        VoucherRequest request = new VoucherRequest(VoucherType.NONE, 100);
        Response response = Response.of(400, "입력한 VoucherType 값이 유효하지 않습니다.");

        //when
        Response createdResponse = voucherConsoleController.create(request);

        //then
        assertThat(createdResponse).usingRecursiveComparison().isEqualTo(response);
    }

    @Test
    @DisplayName("전달받은 DTO의 VoucherType이 유효하면_성공")
    void validVoucherType() {
        //given
        VoucherRequest request = new VoucherRequest(VoucherType.FIXED_AMOUNT, 100);

        //when, then
        assertDoesNotThrow(() -> voucherConsoleController.create(request));
    }

    @Test
    @DisplayName("바우처 생성 요청_성공")
    void requestCreateVoucher() {
        //given
        VoucherRequest request = new VoucherRequest(VoucherType.FIXED_AMOUNT, 100);

        //then
        voucherConsoleController.create(request);

        //when
        verify(voucherService, times(1)).createVoucher(any());
    }

    @Test
    @DisplayName("전달받은 DTO가 NULL이라면 바우처 생성 요청_실패")
    void NotRequestCreateVoucher() {
        //given
        VoucherRequest request = null;
        Response response = Response.of(400, "애플리케이션이 null을 사용하려고 합니다.");

        //when
        Response createdResponse = voucherConsoleController.create(request);

        //then
        verify(voucherService, times(0)).createVoucher(any());
        assertThat(createdResponse).usingRecursiveComparison().isEqualTo(response);
    }

    @Test
    @DisplayName("discountValue 값이 유효하지 않다는 Exception을 받는다면_성공")
    void responseValueRangeException() {
        //given
        VoucherRequest request = new VoucherRequest(VoucherType.FIXED_AMOUNT, 100);
        Response response = Response.of(400, "입력한 DiscountValue 값이 유효하지 않습니다.");
        when(voucherService.createVoucher(any(Voucher.class))).thenThrow(new IllegalArgumentException("입력한 DiscountValue 값이 유효하지 않습니다."));

        //when
        Response createdResponse = voucherConsoleController.create(request);

        //then
        assertThat(createdResponse).usingRecursiveComparison().isEqualTo(response);
    }

    @Test
    @DisplayName("중복된 바우처가 존재한다는 Exception을 받는다면_성공")
    void responseDuplicateVoucherException() {
        //given
        VoucherRequest request = new VoucherRequest(VoucherType.FIXED_AMOUNT, 100);
        Response response = Response.of(400, "입력한 VoucherType과 Discount 값이 중복되었습니다.");
        when(voucherService.createVoucher(any(Voucher.class))).thenThrow(new DuplicateVoucherException("입력한 VoucherType과 Discount 값이 중복되었습니다."));

        //when
        Response createdResponse = voucherConsoleController.create(request);

        //then
        assertThat(createdResponse).usingRecursiveComparison().isEqualTo(response);
    }
}