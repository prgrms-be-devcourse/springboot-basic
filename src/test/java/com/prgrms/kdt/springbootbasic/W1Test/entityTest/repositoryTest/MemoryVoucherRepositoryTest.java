package com.prgrms.kdt.springbootbasic.W1Test.entityTest;

import com.prgrms.kdt.springbootbasic.entity.voucher.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import com.prgrms.kdt.springbootbasic.repository.VoucherRepository;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class MemoryVoucherRepositoryTest {
    private final VoucherRepository voucherRepository;

    public MemoryVoucherRepositoryTest(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

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
