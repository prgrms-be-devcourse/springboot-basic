package com.prgrms.kdt.springbootbasic.W1Test.entityTest.repositoryTest;

import com.prgrms.kdt.springbootbasic.W2Test.Config;
import com.prgrms.kdt.springbootbasic.voucher.entity.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;
import com.prgrms.kdt.springbootbasic.voucher.repository.MemoryVoucherRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SpringJUnitConfig(Config.class)
public class MemoryVoucherRepositoryTest {

    @Autowired
    private MemoryVoucherRepository voucherRepository;


    @Test
    public void testSaveVoucher(){
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId,10);

        Voucher savedVoucher = voucherRepository.saveVoucher(voucher).get();

        assertEquals(voucher, savedVoucher);
    }

    @Test
    public void testGetWithId(){
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId,10);

        voucherRepository.saveVoucher(voucher);

        assertEquals(voucher,voucherRepository.findById(voucherId));
    }

    @Test
    public void testGetAllVouchers(){
        Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

        for (int i = 0; i<10; i++){
            Voucher savedVoucher = voucherRepository.saveVoucher(new FixedAmountVoucher(UUID.randomUUID(),i*10)).get();
            storage.put(savedVoucher.getVoucherId(),savedVoucher);
        }


        assertEquals(storage.values().stream().collect(Collectors.toList()), voucherRepository.getAllVouchers());
    }

}
