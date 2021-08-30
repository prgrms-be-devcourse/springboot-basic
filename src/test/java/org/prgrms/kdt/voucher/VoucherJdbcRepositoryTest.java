package org.prgrms.kdt.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.common.BaseRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 8:02 오후
 */

class VoucherJdbcRepositoryTest extends BaseRepositoryTest {

    @Autowired
    VoucherJdbcRepository voucherJdbcRepository;

    @Test
    @DisplayName("바우처 저장 테스트")
    void insert() {
        voucherJdbcRepository.insert(Voucher.of("치킨 할인 쿠폰", 1000L, VoucherType.FIX));

        int count = voucherJdbcRepository.count();

        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("바우처 ID 조회 테스트")
    void findById() {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = givenPercentVoucher(voucherId);
        voucherJdbcRepository.insert(voucher);

        Optional<Voucher> findVoucher = voucherJdbcRepository.findById(voucherId);

        assertFalse(findVoucher.isEmpty());
        assertThat(findVoucher.get().getVoucherId()).isEqualTo(voucherId);
    }

    @Test
    @DisplayName("바우처 타입 조회 테스트")
    void findByFixedVoucher() {
        voucherJdbcRepository.insert(givenFixedVoucher(UUID.randomUUID()));
        voucherJdbcRepository.insert(givenFixedVoucher(UUID.randomUUID()));
        voucherJdbcRepository.insert(givenFixedVoucher(UUID.randomUUID()));

        voucherJdbcRepository.insert(givenPercentVoucher(UUID.randomUUID()));
        voucherJdbcRepository.insert(givenPercentVoucher(UUID.randomUUID()));

        List<Voucher> vouchers = voucherJdbcRepository.findByVoucherType(VoucherType.FIX);

        assertThat(vouchers.size()).isEqualTo(3);
        assertThat(vouchers).filteredOn(v -> v.getVoucherType() == VoucherType.FIX);
    }

    @Test
    @DisplayName("바우처 이름 수정 테스트")
    void updateNameByVoucher() {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = givenPercentVoucher(voucherId);
        voucherJdbcRepository.insert(voucher);

        String changeName = "different name";
        voucher.changeName(changeName);
        voucherJdbcRepository.update(voucher);

        Optional<Voucher> findVoucher = voucherJdbcRepository.findById(voucherId);

        assertThat(findVoucher).isNotEmpty();
        assertThat(findVoucher.get().getName()).isEqualTo(changeName);
    }

    private Voucher givenFixedVoucher(UUID voucherId) {
        return new Voucher(voucherId, "test voucher", 50L, VoucherType.FIX, LocalDateTime.now());
    }

    private Voucher givenPercentVoucher(UUID voucherId) {
        return new Voucher(voucherId, "test voucher", 50L, VoucherType.PERCENT, LocalDateTime.now());
    }
}