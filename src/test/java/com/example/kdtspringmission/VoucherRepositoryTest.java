package com.example.kdtspringmission;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoucherRepositoryTest {

    VoucherRepository voucherRepository;

    @BeforeEach
    void beforeEach() {
        voucherRepository = new MemoryVoucherRepository();
    }

    @Test
    void testFindById() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(100L);
        Voucher voucher2 = new FixedAmountVoucher(200L);

        //when
        Long voucher1Id = voucherRepository.insert(voucher1);
        Long voucher2Id = voucherRepository.insert(voucher2);
        Voucher findVoucher = voucherRepository.findById(voucher1Id);

        //then
        assertThat(voucher1).isEqualTo(findVoucher);
    }

    @Test
    void testFindAll() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(100L);
        Voucher voucher2 = new FixedAmountVoucher(200L);
        Voucher voucher3 = new FixedAmountVoucher(200L);

        //when
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);

        //then
        assertThat(voucherRepository.findAll().size()).isEqualTo(3);

    }

}