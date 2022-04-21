package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.domain.VoucherType;
import kdt.vouchermanagement.domain.voucher.dto.VoucherRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

        //when, then
        assertThrows(IllegalArgumentException.class, () -> voucherConsoleController.create(request));
    }

    @Test
    @DisplayName("전달받은 DTO의 VoucherType이 유효하면_성공")
    void invalidVoucherType() {
        //given
        VoucherRequest request = new VoucherRequest(VoucherType.FIXED_AMOUNT, 100);

        //when, then
        assertDoesNotThrow(() -> () -> voucherConsoleController.create(request));
    }

    @Test
    @DisplayName("바우처 생성 요청_성공")
    void requestCreateVoucher() {
        //given
        VoucherRequest request = new VoucherRequest(VoucherType.FIXED_AMOUNT, 100);

        //then
        voucherConsoleController.create(request);

        //when
        verify(voucherService, times(1)).createVoucher(any(VoucherRequest.class));
    }
}
