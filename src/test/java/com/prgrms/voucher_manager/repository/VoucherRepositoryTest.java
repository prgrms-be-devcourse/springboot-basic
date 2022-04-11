package com.prgrms.voucher_manager.repository;

import com.prgrms.voucher_manager.exception.WrongVoucherValueException;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.VoucherService;
import org.junit.jupiter.api.*;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoucherRepositoryTest {

    private VoucherRepository voucherRepository;
    private VoucherService voucherService;

    @BeforeEach
    void setRepository(){
        voucherRepository = new MemoryVoucherRepository();
        voucherService = new VoucherService(voucherRepository);
    }

    @Test
    @DisplayName("새로운 voucher 저장시 제대로된 value를 가지고 저장이 되는지 확인하는 테스트")
    void saveVoucher(){
        Assertions.assertEquals(Optional.empty(), voucherService.createFixedAmountVoucher(-1));
        Assertions.assertEquals(Optional.empty(), voucherService.createPercentDiscountVoucher(110));
        Assertions.assertEquals(Optional.class,voucherService.createFixedAmountVoucher(10).getClass());
    }




}