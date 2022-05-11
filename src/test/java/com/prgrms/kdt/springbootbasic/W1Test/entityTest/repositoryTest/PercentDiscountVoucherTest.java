package com.prgrms.kdt.springbootbasic.W1Test.entityTest.repositoryTest;

import com.prgrms.kdt.springbootbasic.W2Test.Config;
import com.prgrms.kdt.springbootbasic.voucher.entity.PercentDiscountVoucher;
import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;
import org.junit.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringJUnitConfig(Config.class)
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
