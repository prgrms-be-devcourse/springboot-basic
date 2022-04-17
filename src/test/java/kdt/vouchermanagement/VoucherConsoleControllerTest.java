package kdt.vouchermanagement;

import kdt.vouchermanagement.domain.voucher.controller.VoucherConsoleController;
import kdt.vouchermanagement.domain.voucher.service.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherConsoleControllerTest {

    @InjectMocks
    VoucherConsoleController voucherConsoleController;

    @Mock
    VoucherService voucherService;

    @Test
    @DisplayName("바우처 생성 요청_성공")
    void requestCreateVoucher() {
        //given
        String voucherTypeNum = "1";
        String discountValue = "1000";

        //then
        voucherConsoleController.create(voucherTypeNum, discountValue);

        //when
        verify(voucherService, times(1)).createVoucher(voucherTypeNum, discountValue);
    }
}
