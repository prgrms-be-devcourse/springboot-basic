package com.prgrms.presentation.command;

import com.prgrms.dto.voucher.VoucherRequest;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.discount.FixedDiscount;
import com.prgrms.presentation.view.ViewManager;
import com.prgrms.service.voucher.VoucherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class CreateCommandTest {

    @Mock
    private VoucherService voucherService;
    @Mock
    private ViewManager viewManager;
    private CreateCommand createCommand;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createCommand = new CreateCommand(voucherService);
    }

    @Test
    @DisplayName("CreateCommand가 고정 할인 정책 요청을 받았을 때 바우처 서비스를 한번 호출한다")
    void createVoucher_CreatedVoucher_Equal() {
        //given
        VoucherRequest voucherRequest = new VoucherRequest(VoucherType.FIXED_AMOUNT_VOUCHER, new FixedDiscount(10));
        Mockito.when(viewManager.guideCreateVoucher()).thenReturn(voucherRequest);

        //when
        createCommand.execute(viewManager);

        //then
        Mockito.verify(voucherService, Mockito.times(1)).createVoucher(voucherRequest);
    }

}
