package com.prgrms.kdt.springbootbasic.serviceTest;

import com.prgrms.kdt.springbootbasic.entity.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.entity.Voucher;
import com.prgrms.kdt.springbootbasic.repository.MemoryVoucherRepository;
import com.prgrms.kdt.springbootbasic.service.VoucherService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@SpringBootTest
public class VoucherServiceTest {
    private final VoucherService voucherService = new VoucherService(new MemoryVoucherRepository());

    @Test
    public void checkVoucherIdDuplicationTest() throws Exception {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId,100);
        voucherService.saveVoucher(voucher);

        assertEquals(false,voucherService.checkVoucherIdDuplication(voucher));

    }

    @Test
    public void saveVoucherTest() throws Exception {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = new FixedAmountVoucher(voucherId,100);

        assertEquals(voucher, voucherService.saveVoucher(voucher));
    }


    @Test
    public void getAllVouchersTest() throws Exception {
        Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

        for (int i = 0; i<10; i++){
            Voucher savedVoucher = voucherService.saveVoucher(new FixedAmountVoucher(UUID.randomUUID(), i * 10));
            storage.put(savedVoucher.getVoucherId(),savedVoucher);
        }


        assertEquals(storage.values().stream().collect(Collectors.toList()), voucherService.getAllVouchers());
    }
}
