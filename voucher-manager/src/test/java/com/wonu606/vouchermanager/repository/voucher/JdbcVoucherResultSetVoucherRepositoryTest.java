package com.wonu606.vouchermanager.repository.voucher;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
import com.wonu606.vouchermanager.domain.voucher.PercentageVoucher;
import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherResultSet;
import com.wonu606.vouchermanager.domain.voucher.discountvalue.FixedAmountValue;
import com.wonu606.vouchermanager.domain.voucher.discountvalue.PercentageDiscountValue;
import com.wonu606.vouchermanager.repository.voucher.JdbcVoucherResultSetRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
@DisplayName("JdbcVoucherResultSetRepository 테스트")
class JdbcVoucherResultSetVoucherRepositoryTest {

    private JdbcVoucherResultSetRepository jdbcVoucherResultSetRepository;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        jdbcVoucherResultSetRepository = new JdbcVoucherResultSetRepository(dataSource);
    }

    @Test
    void save_NewVoucher_VoucherSaved() {
        // given
        Voucher voucher = new FixedAmountVoucher(
                UUID.randomUUID(), new FixedAmountValue(50.0));

        // when
        jdbcVoucherResultSetRepository.save(voucher);
        var result = jdbcVoucherResultSetRepository.findById(voucher.getUuid());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getUuid()).isEqualTo(voucher.getUuid());
        assertThat(result.get().getSimpleName()).isEqualTo(voucher.getClass().getSimpleName());
        assertThat(result.get().getDiscountValue()).isEqualTo(voucher.getDiscountValue());
    }

    @Test
    void findById_VoucherPresent_ReturnsSameVoucher() {
        // given
        Voucher voucher = new FixedAmountVoucher(
                UUID.randomUUID(), new FixedAmountValue(50.0));
        jdbcVoucherResultSetRepository.save(voucher);

        // when
        Optional<VoucherResultSet> foundVoucher = jdbcVoucherResultSetRepository.findById(voucher.getUuid());

        // then
        assertThat(foundVoucher).isPresent();
        assertThat(foundVoucher.get().getUuid()).isEqualTo(voucher.getUuid());
    }

    @Test
    void findById_VoucherNotPresent_ReturnsEmpty() {
        // given
        UUID nonExistentId = UUID.randomUUID();

        // when
        Optional<VoucherResultSet> foundVoucher = jdbcVoucherResultSetRepository.findById(nonExistentId);

        // then
        assertThat(foundVoucher).isNotPresent();
    }

    @Test
    void findAll_VouchersPresent_ReturnsAllVouchers() {
        // given
        Voucher voucher1 = new FixedAmountVoucher(
                UUID.randomUUID(), new FixedAmountValue(50.0));
        Voucher voucher2 = new PercentageVoucher(
                UUID.randomUUID(), new PercentageDiscountValue(30.0));
        jdbcVoucherResultSetRepository.save(voucher1);
        jdbcVoucherResultSetRepository.save(voucher2);

        // when
        List<VoucherResultSet> allVouchers = jdbcVoucherResultSetRepository.findAll();

        // then
        assertThat(allVouchers).hasSize(2);
        assertThat(allVouchers).extracting("uuid").contains(voucher1.getUuid(), voucher2.getUuid());
    }

    @Test
    void deleteById_VoucherPresent_VoucherDeleted() {
        // given
        Voucher voucher = new FixedAmountVoucher(
                UUID.randomUUID(), new FixedAmountValue(50.0));
        jdbcVoucherResultSetRepository.save(voucher);

        // then
        jdbcVoucherResultSetRepository.deleteById(voucher.getUuid());
        Optional<VoucherResultSet> foundVoucher = jdbcVoucherResultSetRepository.findById(voucher.getUuid());

        // when
        assertThat(foundVoucher).isNotPresent();
    }

    @Test
    void deleteAll_MultipleVouchers_AllVouchersDeleted() {
        // given
        Voucher voucher1 = new FixedAmountVoucher(
                UUID.randomUUID(), new FixedAmountValue(50.0));
        Voucher voucher2 = new PercentageVoucher(
                UUID.randomUUID(), new PercentageDiscountValue(30.0));
        jdbcVoucherResultSetRepository.save(voucher1);
        jdbcVoucherResultSetRepository.save(voucher2);

        // then
        jdbcVoucherResultSetRepository.deleteAll();
        List<VoucherResultSet> allVouchers = jdbcVoucherResultSetRepository.findAll();

        // when
        assertThat(allVouchers).isEmpty();
    }
}
