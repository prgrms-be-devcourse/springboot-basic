package com.prgrms.kdt.springbootbasic.W1Test.entityTest;

import com.prgrms.kdt.springbootbasic.entity.voucher.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

import java.util.UUID;

@SpringBootTest
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
