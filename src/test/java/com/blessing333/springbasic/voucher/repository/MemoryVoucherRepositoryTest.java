package com.blessing333.springbasic.voucher.repository;

import com.blessing333.springbasic.voucher.domain.FixedAmountVoucher;
import com.blessing333.springbasic.voucher.domain.PercentDiscountVoucher;
import com.blessing333.springbasic.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

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
        Optional<Voucher> foundVoucher = repository.findById(fixedAmountVoucherId);
        assertTrue(foundVoucher.isEmpty());
    }

    @DisplayName("바우쳐 저장 테스트")
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