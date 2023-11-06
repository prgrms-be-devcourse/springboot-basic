package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VoucherJDBCRepositoryTest {
    private final static UUID NON_EXISTENT_VOUCHER_ID = UUID.randomUUID();
    NamedParameterJdbcTemplate jdbcTemplate;
    VoucherJDBCRepository voucherJDBCRepository;

    @Autowired
    VoucherJDBCRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        voucherJDBCRepository = new VoucherJDBCRepository(this.jdbcTemplate);
    }

    @Test
    @DisplayName("🆗 고정 금액 할인 바우처를 추가할 수 있다.")
    void saveFixedAmountVoucher() {
        Voucher voucher = new Voucher("FIXED", 1000);
        voucherJDBCRepository.insert(voucher);

        Optional<Voucher> retrievedVoucher = voucherJDBCRepository.findById(voucher.getId());

        assertThat(retrievedVoucher).isNotEmpty();
        assertThat(retrievedVoucher.get().getId()).isEqualTo(voucher.getId());
    }

    @Test
    @DisplayName("🆗 퍼센트 할인 바우처를 추가할 수 있다.")
    void savePercentVoucher() {
        Voucher voucher = new Voucher("PERCENT", 50);
        voucherJDBCRepository.insert(voucher);

        Optional<Voucher> retrievedVoucher = voucherJDBCRepository.findById(voucher.getId());

        assertThat(retrievedVoucher.isEmpty()).isFalse();
        assertThat(retrievedVoucher.get().getId()).isEqualTo(voucher.getId());
    }

    @Test
    @DisplayName("🆗 모든 바우처를 조회할 수 있다. 단, 없다면 빈 list를 반환한다.")
    void findAllVoucher() {
        for (int i = 1; i < 6; i++)
            voucherJDBCRepository.insert(new Voucher("PERCENT", i));

        List<Voucher> vouchers = voucherJDBCRepository.findAll();

        assertThat(vouchers.size()).isGreaterThanOrEqualTo(5);
    }

    @Test
    @DisplayName("🆗 바우처를 아이디로 조회할 수 있다.")
    void findVoucherById() {
        Voucher voucher = new Voucher("FIXED", 1234);
        voucherJDBCRepository.insert(voucher);

        Optional<Voucher> retrievedVoucher = voucherJDBCRepository.findById(voucher.getId());

        assertThat(retrievedVoucher.isPresent()).isTrue();
        assertThat(retrievedVoucher.get().getId()).isEqualTo(voucher.getId());
        assertThat(retrievedVoucher.get().getDiscountValue()).isEqualTo(voucher.getDiscountValue());
        assertThat(retrievedVoucher.get().getTypeName()).isEqualTo(voucher.getTypeName());
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
        Voucher voucher = new Voucher("FIXED", 5555);
        voucherJDBCRepository.insert(voucher);

        voucherJDBCRepository.delete(voucher.getId());

        assertThat(voucherJDBCRepository.findById(voucher.getId()).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("🚨 해당하는 바우처가 없다면, 바우처를 아이디로 삭제할 수 없다.")
    void deleteNonExistentVoucher() {
        assertThrows(RuntimeException.class, () -> voucherJDBCRepository.delete(NON_EXISTENT_VOUCHER_ID));
    }

    @Test
    @DisplayName("🆗 바우처를 업데이트 할 수 있다.")
    void updateVoucher() {
        Voucher voucher = new Voucher("FIXED", 5555);
        voucherJDBCRepository.insert(voucher);

        Voucher updatedVoucher = new Voucher(voucher.getId(), voucher.getCreatedAt(), "PERCENT", 100);
        voucherJDBCRepository.update(updatedVoucher);

        Optional<Voucher> retrievedVoucher = voucherJDBCRepository.findById(voucher.getId());
        assertThat(retrievedVoucher.isEmpty()).isFalse();
        assertThat(retrievedVoucher.get().getDiscountValue()).isEqualTo(updatedVoucher.getDiscountValue());
        assertThat(retrievedVoucher.get().getTypeName()).isEqualTo(updatedVoucher.getTypeName());
    }

    @Test
    @DisplayName("🚨 해당하는 바우처가 없다면, 바우처를 업데이트 할 수 없다.")
    void updateNonExistentVoucher() {
        assertThrows(NoSuchElementException.class, () -> voucherJDBCRepository.update(new Voucher(NON_EXISTENT_VOUCHER_ID, LocalDateTime.now(), "PERCENT", 100)));
    }
}