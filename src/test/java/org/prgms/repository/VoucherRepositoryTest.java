package org.prgms.repository;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.domain.FixedAmountVoucher;
import org.prgms.domain.PercentDiscountVoucher;
import org.prgms.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class VoucherRepositoryTest {
    @Autowired
    private VoucherRepository voucherRepository;

    private final Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
    private final Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

    @BeforeEach
    void deleteAll() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처 저장 테스트")
    void saveTest() {
        voucherRepository.save(voucher);

        val vouchers = voucherRepository.findAll();

        assertThat(vouchers).containsExactly(voucher);
    }

    @Test
    @DisplayName("바우처 조회 테스트")
    void findAllTest() {
        voucherRepository.save(voucher);
        voucherRepository.save(voucher2);

        val vouchers = voucherRepository.findAll();

        assertThat(vouchers).containsExactlyInAnyOrder(voucher, voucher2);
    }

    @Test
    @DisplayName("바우처 id로 조회 테스트")
    void findByIdTest() {
        voucherRepository.save(voucher);

        var unknown = voucherRepository.findById(UUID.randomUUID());
        var foundVoucher = voucherRepository.findById(voucher.getVoucherId());

        assertThat(unknown.isEmpty()).isTrue();
        assertThat(foundVoucher.orElseThrow()).isEqualTo(voucher);
    }
}