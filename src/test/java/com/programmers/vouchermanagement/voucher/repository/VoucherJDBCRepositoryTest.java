package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.TestConfig;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
class VoucherJDBCRepositoryTest {
    private final static UUID NON_EXISTENT_VOUCHER_ID = UUID.randomUUID();
    @Autowired
    VoucherJDBCRepository voucherJDBCRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @AfterAll
    void init() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        jdbcTemplate.execute("TRUNCATE TABLE test.vouchers");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }

    @Test
    @DisplayName("🆗 고정 금액 할인 바우처를 추가할 수 있다.")
    void saveFixedAmountVoucher() {
        Voucher voucher = new Voucher(UUID.randomUUID(), 555, VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);

        Optional<Voucher> retrievedVoucher = voucherJDBCRepository.findById(voucher.voucherId());

        assertThat(retrievedVoucher.isEmpty()).isFalse();
        assertThat(retrievedVoucher.get().voucherId()).isEqualTo(voucher.voucherId());
    }

    @Test
    @DisplayName("🆗 퍼센트 할인 바우처를 추가할 수 있다.")
    void savePercentVoucher() {
        Voucher voucher = new Voucher(UUID.randomUUID(), 50, VoucherType.PERCENT);
        voucherJDBCRepository.save(voucher);

        Optional<Voucher> retrievedVoucher = voucherJDBCRepository.findById(voucher.voucherId());

        assertThat(retrievedVoucher.isEmpty()).isFalse();
        assertThat(retrievedVoucher.get().voucherId()).isEqualTo(voucher.voucherId());
    }

    @Test
    @DisplayName("🆗 모든 바우처를 조회할 수 있다. 단, 없다면 빈 list를 반환한다.")
    void findAllVoucher() {
        for (int i = 1; i < 6; i++)
            voucherJDBCRepository.save(new Voucher(UUID.randomUUID(), i * 100, VoucherType.PERCENT));

        List<Voucher> vouchers = voucherJDBCRepository.findAll();

        assertThat(vouchers.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("🆗 바우처를 아이디로 조회할 수 있다.")
    void findVoucherById() {
        Voucher voucher = new Voucher(UUID.randomUUID(), 1234, VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);

        Optional<Voucher> retrievedVoucher = voucherJDBCRepository.findById(voucher.voucherId());

        assertThat(retrievedVoucher.isPresent()).isTrue();
        assertThat(retrievedVoucher.get().voucherId()).isEqualTo(voucher.voucherId());
        assertThat(retrievedVoucher.get().discountValue()).isEqualTo(voucher.discountValue());
        assertThat(retrievedVoucher.get().voucherType()).isEqualTo(voucher.voucherType());
    }

    @Test
    @DisplayName("🚨 해당하는 바우처가 없다면, 바우처를 아이디로 조회할 수 없다.")
    void findNonExistentVoucherById() {
        Optional<Voucher> retrievedVoucher = voucherJDBCRepository.findById(NON_EXISTENT_VOUCHER_ID);

        assertThat(retrievedVoucher.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("🆗 바우처를 아이디로 삭제할 수 있다.")
    void deleteVoucher() {
        Voucher voucher = new Voucher(UUID.randomUUID(), 5555, VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);

        voucherJDBCRepository.delete(voucher.voucherId());

        assertThat(voucherJDBCRepository.findById(voucher.voucherId()).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("🚨 해당하는 바우처가 없다면, 바우처를 아이디로 삭제할 수 없다.")
    void deleteNonExistentVoucher() {
        assertThrows(RuntimeException.class, () -> voucherJDBCRepository.delete(NON_EXISTENT_VOUCHER_ID));
    }

    @Test
    @DisplayName("🆗 바우처를 업데이트 할 수 있다.")
    void updateVoucher() {
        Voucher voucher = new Voucher(UUID.randomUUID(), 5555, VoucherType.FIXED);
        voucherJDBCRepository.save(voucher);

        Voucher updatedVoucher = new Voucher(voucher.voucherId(), 100, VoucherType.PERCENT);
        voucherJDBCRepository.update(updatedVoucher);

        Optional<Voucher> retrievedVoucher = voucherJDBCRepository.findById(voucher.voucherId());
        assertThat(retrievedVoucher.isEmpty()).isFalse();
        assertThat(retrievedVoucher.get().discountValue()).isEqualTo(updatedVoucher.discountValue());
        assertThat(retrievedVoucher.get().voucherType()).isEqualTo(updatedVoucher.voucherType());
    }

    @Test
    @DisplayName("🚨 해당하는 바우처가 없다면, 바우처를 업데이트 할 수 없다.")
    void updateNonExistentVoucher() {
        assertThrows(RuntimeException.class, () -> voucherJDBCRepository.update(new Voucher(NON_EXISTENT_VOUCHER_ID, 100, VoucherType.PERCENT)));
    }
}