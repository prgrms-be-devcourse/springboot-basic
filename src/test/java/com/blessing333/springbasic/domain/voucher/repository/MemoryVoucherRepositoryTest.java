package com.blessing333.springbasic.domain.voucher.repository;

import com.blessing333.springbasic.domain.voucher.model.Voucher;
import com.blessing333.springbasic.domain.voucher.model.Voucher.VoucherType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemoryVoucherRepositoryTest {
    MemoryVoucherRepository repository = new MemoryVoucherRepository();

    @AfterEach
    void resetRepository(){
        repository.deleteAll();
    }

    @DisplayName("존재하지 않는 바우쳐에 조회를 시도")
    @Test
    void findVoucherWithInvalidId(){
        UUID invalidId = UUID.randomUUID();
        Optional<Voucher> foundVoucher = repository.findById(invalidId);
        assertTrue(foundVoucher.isEmpty());
    }

    @DisplayName("존재하는 바우쳐에 조회 시도")
    @Test
    void findVoucherWithCollectId(){
        UUID fixedAmountVoucherId = UUID.randomUUID();
        Voucher fixedAmountVoucher = new Voucher(fixedAmountVoucherId,VoucherType.FIXED,1000);
        repository.insert(fixedAmountVoucher);

        Optional<Voucher> foundVoucher = repository.findById(fixedAmountVoucherId);

        assertTrue(foundVoucher.isPresent());
        assertThat(fixedAmountVoucher).isEqualTo(foundVoucher.get());
    }

    @DisplayName("저장된 바우쳐 전체 조회")
    @Test
    void findAllTest(){
        Voucher fixedAmountVoucher = new Voucher(UUID.randomUUID(),VoucherType.FIXED,1000);
        Voucher percentDiscountVoucher = new Voucher(UUID.randomUUID(),VoucherType.PERCENT,50);
        repository.insert(fixedAmountVoucher);
        repository.insert(percentDiscountVoucher);

        List<Voucher> all = repository.findAll();

        assertThat(all).hasSize(2);
        assertTrue(all.contains(fixedAmountVoucher));
        assertTrue(all.contains(percentDiscountVoucher));
    }

    @DisplayName("바우쳐 저장")
    @Test
    void save() {
        UUID fixedAmountVoucherId = UUID.randomUUID();
        UUID percentVoucherId =UUID.randomUUID();
        Voucher fixedAmountVoucher = new Voucher(fixedAmountVoucherId,VoucherType.FIXED,1000);
        Voucher percentDiscountVoucher = new Voucher(percentVoucherId,VoucherType.PERCENT,50);


        repository.insert(fixedAmountVoucher);
        repository.insert(percentDiscountVoucher);

        assertNotNull(repository.findById(fixedAmountVoucherId).get());
        assertNotNull(repository.findById(percentVoucherId).get());
    }

}