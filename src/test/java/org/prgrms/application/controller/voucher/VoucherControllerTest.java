//package org.prgrms.application.controller.voucher;
//
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.prgrms.application.domain.voucher.Voucher;
//import org.prgrms.application.domain.voucher.VoucherType;
//import org.prgrms.application.service.VoucherService;
//import org.springframework.ui.Model;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class VoucherControllerTest {
//
//    @Mock
//    VoucherService voucherService;
//
//    private VoucherController voucherController;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        voucherController = new VoucherController(voucherService);
//    }
//
//    @Test
//    @DisplayName("바우처를 조회를 성공한다.")
//    void getVouchersSuccessTest(){
//        //given
//        List<Voucher> vouchers = Arrays.asList(
//                new Voucher(1L, VoucherType.FIXED, 1000),
//                new Voucher(2L, VoucherType.PERCENT, 20)
//        );
//
//        Model model = mock(Model.class);
//        when(voucherService.getVouchers()).thenReturn(vouchers);
//
//        //when
//        String viewName = voucherController.findVouchers(model);
//
//        // then
//        verify(voucherService, times(1)).getVouchers();
//        verify(model, times(1)).addAttribute("vouchers", vouchers);
//        assertEquals("vouchers", viewName);
//
//    }
//
//
//
//}