package com.prgrms.management.voucher.service;

import com.prgrms.management.voucher.repository.MemoryVoucherRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {
    @Mock
    MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

    @InjectMocks
    VoucherService voucherService;

//    @Test
//    void 바우처_리스트_조회() {
//        //given
//        FixedAmountVoucher fixedVoucher = new FixedAmountVoucher(1000);
//        PercentAmountVoucher percentVoucher = new PercentAmountVoucher(10);
//        List<Voucher> voucherList = Arrays.asList(fixedVoucher, percentVoucher);
//        //즉시 인스턴스 객체 반환
//        when(voucherService.findAll()).thenReturn(voucherList);
//        //when
//        List<Voucher> vouchers = voucherService.findAll();
//        //then
//        Assertions.assertThat(vouchers.size()).isEqualTo(2);
//    }
//
//    @Test
//    void 비어있는_리스트_조회() {
//        //given
//        List<Voucher> voucherList = new ArrayList<>();
//        //즉시 인스턴스 객체 반환
//        when(memoryVoucherRepository.findAll()).thenReturn(voucherList);
//        //when
//        List<Voucher> vouchers = voucherService.findAll();
//        //then
//        Assertions.assertThat(vouchers.size()).isEqualTo(0);
//    }
}