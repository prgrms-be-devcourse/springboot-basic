package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherInMemoryRepositoryTest {
    VoucherInMemoryRepository voucherInMemoryRepository = new VoucherInMemoryRepository();

    @Test
    @DisplayName("🆗 바우처를 아이디로 삭제할 수 있다.")
    void deleteVoucherSucceed() {
        Voucher voucher = new Voucher("FIXED", 5555);
        voucherInMemoryRepository.insert(voucher);

        voucherInMemoryRepository.delete(voucher.getId());

        assertThat(voucherInMemoryRepository.findById(voucher.getId()).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("🚨 없는 바우처를 삭제하면 실패한다.")
    void deleteNonExistVoucherFail() {
        UUID NonExistVoucherId = UUID.randomUUID();

        assertThrows(RuntimeException.class, () -> voucherInMemoryRepository.delete(NonExistVoucherId));
    }

    @Test
    @DisplayName("🆗 바우처를 업데이트 할 수 있다.")
    void updateVoucherSucceed() {
        Voucher voucher = new Voucher("FIXED", 5555);
        voucherInMemoryRepository.insert(voucher);

        Voucher updatedVoucher = new Voucher(voucher.getId(), voucher.getCreatedAt(), "PERCENT", 100);
        voucherInMemoryRepository.update(updatedVoucher);

        Optional<Voucher> retrievedVoucher = voucherInMemoryRepository.findById(voucher.getId());
        assertThat(retrievedVoucher.isEmpty()).isFalse();
        assertThat(retrievedVoucher.get().getDiscountValue()).isEqualTo(updatedVoucher.getDiscountValue());
        assertThat(retrievedVoucher.get().getTypeName()).isEqualTo(updatedVoucher.getTypeName());
    }
}