package com.waterfogsw.voucher.voucher.repository;

import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.domain.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class VoucherRepositoryTests {

    private VoucherRepository repository = new VoucherMemoryRepository();

    @Test
    @DisplayName("바우처 조회")
    public void saveVoucher() throws Exception {
        // given
        Voucher voucher = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 5000d);
        repository.save(voucher);

        // when
        var find = repository.findById(voucher.getId());

        // then
        assertEquals(voucher, find);
    }

    @Test
    @DisplayName("바우처 전체 조회 - 모두 조회되면 성공")
    public void findAllVoucher() throws Exception {
        // given
        Voucher voucher1 = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 5000d);
        Voucher voucher2 = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 5000d);
        Voucher voucher3 = new Voucher(UUID.randomUUID(), VoucherType.FIXED_AMOUNT, 5000d);

        repository.save(voucher1);
        repository.save(voucher2);
        repository.save(voucher3);

        // when
        var findVouchers = repository.findAll();

        // then
        assertEquals(findVouchers, containsInAnyOrder(voucher1, voucher2, voucher3));
    }
}
