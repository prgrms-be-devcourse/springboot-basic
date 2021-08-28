package org.prgms.w3d1.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.w3d1.model.voucher.FixedAmountVoucher;
import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.voucher.VoucherService;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MemoryVoucherRepositoryTest {

    /*
        테스트 : 메모리에서 특정 바우처를 찾는지
        Given : Voucher를 메모리에다가 추가
        when : findById 메서드로 찾을 때
        then : 메모리에 넣은 바우처를 찾아와야함
     */
    @Test
    @DisplayName("메모리에서 특정 바우처를 찾는지 확인한다")
    void findById() {
        // Given
        Voucher voucher = FixedAmountVoucher.of(UUID.randomUUID(), 100L);
        MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();
        memoryVoucherRepository.save(voucher);

        // When
        var testVoucher = memoryVoucherRepository.findById(voucher.getVoucherId());

        // then
        assertThat(testVoucher.get(), samePropertyValuesAs(voucher));
    }


    /*
        테스트 : 메모리에서 특정 바우처를 저장했는지
        Given : 저장소 생성
        when : save 메서드를 실행하고, findAll 메서드로 List를 꺼내오면
        then : List의 사이즈가 1이여야함
     */
    @Test
    void save() {
        // Given
        MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();

        // When
        memoryVoucherRepository.save(FixedAmountVoucher.of(UUID.randomUUID(), 100L));
        var testVouchers = memoryVoucherRepository.findAll();

        // then
        assertThat(testVouchers.size(), is(1));

    }

    /*
        테스트 : 메모리에서 모든 바우처를 꺼내는지
        Given : 3개의 바우처를 만들어 저장소에 저장
        when : save 메서드를 실행하고, findAll 메서드로 List를 꺼내오면
        then : list의 모든 맴버가 같아야함
     */
    @Test
    void findAll() {
        // Given
        MemoryVoucherRepository memoryVoucherRepository = new MemoryVoucherRepository();
        var testVoucher1 = FixedAmountVoucher.of(UUID.randomUUID(), 100L);
        var testVoucher2 = FixedAmountVoucher.of(UUID.randomUUID(), 200L);
        var testVoucher3 = FixedAmountVoucher.of(UUID.randomUUID(), 300L);

        // When
        memoryVoucherRepository.save(testVoucher1);
        memoryVoucherRepository.save(testVoucher2);
        memoryVoucherRepository.save(testVoucher3);
        var testVouchers = memoryVoucherRepository.findAll();

        // then
        assertThat(testVouchers, containsInAnyOrder(testVoucher1, testVoucher2, testVoucher3));
    }
}