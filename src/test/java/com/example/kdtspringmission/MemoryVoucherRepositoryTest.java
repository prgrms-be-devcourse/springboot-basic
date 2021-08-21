package com.example.kdtspringmission;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.kdtspringmission.voucher.domain.FixedAmountVoucher;
import com.example.kdtspringmission.voucher.domain.Voucher;
import com.example.kdtspringmission.voucher.repository.MemoryVoucherRepository;
import com.example.kdtspringmission.voucher.repository.VoucherRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemoryVoucherRepositoryTest {

    VoucherRepository voucherRepository;

    @BeforeEach
    void beforeEach() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @Test
    void testFindById() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(),100L);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(),200L);

        //when
        UUID voucher1Id = voucherRepository.insert(voucher1);
        UUID voucher2Id = voucherRepository.insert(voucher2);
        Voucher findVoucher = voucherRepository.findById(voucher1Id);

        //then
        assertThat(voucher1).isEqualTo(findVoucher);
    }

    @Test
    void testFindAll() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(),100L);
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(),200L);
        Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(),200L);

        //when
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);

        //then
        assertThat(voucherRepository.findAll().size()).isEqualTo(3);

    }

}