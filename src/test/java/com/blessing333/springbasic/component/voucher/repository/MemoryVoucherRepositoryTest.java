package com.blessing333.springbasic.component.voucher.repository;

import com.blessing333.springbasic.voucher.domain.FixedAmountVoucher;
import com.blessing333.springbasic.voucher.domain.PercentDiscountVoucher;
import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.repository.MemoryVoucherRepository;
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
        Voucher fixedAmountVoucher = new FixedAmountVoucher(1000);
        UUID fixedAmountVoucherId = fixedAmountVoucher.getVoucherId();
        repository.save(fixedAmountVoucher);

        Optional<Voucher> foundVoucher = repository.findById(fixedAmountVoucherId);

        assertTrue(foundVoucher.isPresent());
        assertThat(fixedAmountVoucher).isEqualTo(foundVoucher.get());
    }

    @DisplayName("저장된 바우쳐 전체 조회")
    @Test
    void findAllTest(){
        Voucher fixedAmountVoucher = generateVoucher(VoucherType.FIXED);
        Voucher percentDiscountVoucher = generateVoucher(VoucherType.PERCENT);
        repository.save(fixedAmountVoucher);
        repository.save(percentDiscountVoucher);

        List<Voucher> all = repository.findAll();

        assertThat(all).hasSize(2);
        assertTrue(all.contains(fixedAmountVoucher));
        assertTrue(all.contains(percentDiscountVoucher));
    }

    @DisplayName("바우쳐 저장")
    @Test
    void save() {
        Voucher fixedAmountVoucher = generateVoucher(VoucherType.FIXED);
        Voucher percentDiscountVoucher = generateVoucher(VoucherType.PERCENT);
        UUID fixedAmountVoucherId = fixedAmountVoucher.getVoucherId();
        UUID percentVoucherId = percentDiscountVoucher.getVoucherId();

        repository.save(fixedAmountVoucher);
        repository.save(percentDiscountVoucher);

        assertNotNull(repository.findById(fixedAmountVoucherId).get());
        assertNotNull(repository.findById(percentVoucherId).get());
    }

    @DisplayName("바우쳐 삭제 테스트")
    @Test
    void delete() {
        Voucher fixedAmountVoucher = generateVoucher(VoucherType.FIXED);
        UUID fixedAmountVoucherId = fixedAmountVoucher.getVoucherId();
        repository.save(fixedAmountVoucher);

        repository.delete(fixedAmountVoucherId);

        Optional<Voucher> found = repository.findById(fixedAmountVoucherId);
        assertTrue(found.isEmpty());
    }

    private Voucher generateVoucher(VoucherType type){
        return switch (type) {
            case FIXED -> new FixedAmountVoucher(1000);
            case PERCENT -> new PercentDiscountVoucher(50);
        };
    }

    enum VoucherType{
        FIXED,PERCENT
    }
}