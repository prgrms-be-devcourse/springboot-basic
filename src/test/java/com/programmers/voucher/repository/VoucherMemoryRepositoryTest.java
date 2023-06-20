package com.programmers.voucher.repository;

import com.programmers.voucher.domain.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.programmers.voucher.testutil.VoucherTestUtil.createFixedVoucher;
import static org.assertj.core.api.Assertions.assertThat;

class VoucherMemoryRepositoryTest {
    private VoucherRepository voucherRepository;

    @BeforeEach
    void init() {
        voucherRepository = new VoucherMemoryRepository();
    }

    @Test
    void save() {
        //given
        UUID voucherId = UUID.randomUUID();
        Voucher fixedVoucher = createFixedVoucher(voucherId, 10);

        voucherRepository.save(fixedVoucher);

        //when
        List<Voucher> result = voucherRepository.findAll();

        //then
        assertThat(result).contains(fixedVoucher);
    }

    @Test
    void findAll() {
        //given
        Voucher fixedVoucherA = createFixedVoucher(UUID.randomUUID(), 10);
        Voucher fixedVoucherB = createFixedVoucher(UUID.randomUUID(), 10);

        voucherRepository.save(fixedVoucherA);
        voucherRepository.save(fixedVoucherB);

        //when
        List<Voucher> result = voucherRepository.findAll();

        //then
        assertThat(result).contains(fixedVoucherA, fixedVoucherB);
    }
}