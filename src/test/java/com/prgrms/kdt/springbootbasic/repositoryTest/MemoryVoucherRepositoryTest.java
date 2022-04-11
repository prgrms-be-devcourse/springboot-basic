package com.prgrms.kdt.springbootbasic.repositoryTest;

import com.prgrms.kdt.springbootbasic.entity.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.entity.Voucher;
import com.prgrms.kdt.springbootbasic.repository.MemoryVoucherRepository;
import com.prgrms.kdt.springbootbasic.repository.VoucherRepository;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@SpringBootTest
public class MemoryVoucherRepositoryTest {
    private static VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @Test
    public void testSaveVoucher(){
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId,10);

        Voucher savedVoucher = voucherRepository.saveVoucher(voucher);

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
            Voucher savedVoucher = voucherRepository.saveVoucher(new FixedAmountVoucher(UUID.randomUUID(),i*10));
            storage.put(savedVoucher.getVoucherId(),savedVoucher);
        }


        assertEquals(storage.values().stream().collect(Collectors.toList()), voucherRepository.getAllVouchers());
    }

}
