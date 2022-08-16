package com.prgrms.kdt.springbootbasic.W1Test.entityTest.repositoryTest;

import com.prgrms.kdt.springbootbasic.W2Test.Config;
import com.prgrms.kdt.springbootbasic.voucher.entity.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

@SpringJUnitConfig(Config.class)
public class FixedAmountVoucherTest {

    @Test
    public void testGetVoucherId(){
        UUID voucherId = UUID.randomUUID();

        Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId,10);

        assertEquals(voucherId, fixedAmountVoucher.getVoucherId());
    }

    @Test
    public void testDiscount(){
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),10);

        assertEquals(90, fixedAmountVoucher.getDiscountedMoney(100));
    }
}
