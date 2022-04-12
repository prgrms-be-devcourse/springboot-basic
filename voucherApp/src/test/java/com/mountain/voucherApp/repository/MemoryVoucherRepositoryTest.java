package com.mountain.voucherApp.repository;

import com.mountain.voucherApp.voucher.PercentDiscountVoucher;
import com.mountain.voucherApp.voucher.Voucher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository voucherRepository;

    @BeforeEach
    void init() {
        voucherRepository = new MemoryVoucherRepository();
        voucherRepository.clear();
    }

    @DisplayName("저장 테스트")
    @Test
    public void insertTest() throws Exception {
        //given
        Voucher voucher = new PercentDiscountVoucher(10);
        //when
        Voucher saved = voucherRepository.insert(voucher);
        //then
        Optional<Voucher> findVoucher = voucherRepository.findById(saved.getVoucherId());
        Assertions.assertEquals(saved.getVoucherId(), findVoucher.get().getVoucherId());
    }

    @DisplayName("findAll 테스트")
    @Test
    public void findAllTest() throws Exception {
        //given
        for (int i = 1; i <= 10; i++) {
            Voucher voucher = new PercentDiscountVoucher(i);
            voucherRepository.insert(voucher);
        }
        //when
        List<Voucher> all = voucherRepository.findAll();
        System.out.println(all);
        //then
        Assertions.assertEquals(10, all.size());
    }

}