package com.mountain.voucherApp.voucher;

import com.mountain.voucherApp.repository.MemoryVoucherRepository;
import com.mountain.voucherApp.repository.VoucherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import static com.mountain.voucherApp.utils.DiscountPolicyUtil.*;

class VoucherServiceTest {

    private VoucherRepository voucherRepository = new MemoryVoucherRepository();
    private VoucherService voucherService = new VoucherService(voucherRepository);

    @DisplayName("createVoucher 테스트")
    @Test
    public void createVoucherTest() throws Exception {
        //given
        for (int i = 0; i < 7; i++) {
            voucherService.createVoucher(i % 2 + 1, i);
        }
        //when
        List<Voucher> all = voucherService.findAll();
        //then
        Assertions.assertEquals(7, all.size());
    }

}