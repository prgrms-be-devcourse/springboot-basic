package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InMemoryVoucherRepositoryTest {
    InMemoryVoucherRepository inMemoryVoucherRepository = new InMemoryVoucherRepository();

    @Test
    @DisplayName("바우처를 아이디로 삭제할 수 있다.")
    void deleteVoucherSucceed() {
        Voucher voucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(5555), VoucherType.FIXED);
        inMemoryVoucherRepository.save(voucher);

        inMemoryVoucherRepository.delete(voucher.getVoucherId());

        assertThat(inMemoryVoucherRepository.findById(voucher.getVoucherId()).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("없는 바우처를 삭제하면 실패한다.")
    void deleteNonExistVoucherFail() {
        UUID NonExistVoucherId = UUID.randomUUID();

        assertThrows(RuntimeException.class, () -> {
            inMemoryVoucherRepository.delete(NonExistVoucherId);
        });
    }

    @Test
    @DisplayName("바우처를 업데이트 할 수 있다.")
    void updateVoucherSucceed() {
        Voucher voucher = new Voucher(UUID.randomUUID(), BigDecimal.valueOf(5555), VoucherType.FIXED);
        inMemoryVoucherRepository.save(voucher);

        Voucher updatedVoucher = new Voucher(voucher.getVoucherId(), BigDecimal.valueOf(100), VoucherType.PERCENT);
        inMemoryVoucherRepository.update(updatedVoucher);

        Optional<Voucher> retrievedVoucher = inMemoryVoucherRepository.findById(voucher.getVoucherId());
        assertThat(retrievedVoucher.isEmpty()).isFalse();
        assertThat(retrievedVoucher.get().getDiscountValue()).isEqualTo(updatedVoucher.getDiscountValue());
        assertThat(retrievedVoucher.get().getVoucherType()).isEqualTo(updatedVoucher.getVoucherType());
    }
}