package com.prgrms.kdt.springbootbasic.W1Test.entityTest;

import com.prgrms.kdt.springbootbasic.entity.voucher.PercentDiscountVoucher;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.Assert.*;

@SpringBootTest
public class PercentDiscountVoucherTest {

    @Test
    public void testGetVoucherId(){
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new PercentDiscountVoucher(voucherId,10);

        assertEquals(voucherId, voucher.getVoucherId());
    }

    @Test
    public void testDiscount(){
        Voucher voucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);

        assertEquals(90, voucher.getDiscountedMoney(100));
    }
}
