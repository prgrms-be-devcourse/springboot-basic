//package com.wonu606.vouchermanager.repository.voucher;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.wonu606.vouchermanager.domain.voucher.FixedAmountVoucher;
//import com.wonu606.vouchermanager.domain.voucher.PercentageVoucher;
//import com.wonu606.vouchermanager.domain.voucher.Voucher;
//import com.wonu606.vouchermanager.repository.voucher.reader.VoucherJdbcReader;
//import com.wonu606.vouchermanager.repository.voucher.reader.rowmapper.VoucherReaderRowMapperManager;
//import com.wonu606.vouchermanager.repository.voucher.resultset.VoucherCreateResultSet;
//import com.wonu606.vouchermanager.domain.voucher.discountvalue.FixedAmountValue;
//import com.wonu606.vouchermanager.domain.voucher.discountvalue.PercentageDiscountValue;
//import com.wonu606.vouchermanager.repository.voucher.store.VoucherJdbcStore;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//import javax.sql.DataSource;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//
//@JdbcTest
//@DisplayName("JdbcVoucherResultSetRepository 테스트")
//class VoucherJdbcRepositoryTest {
//
//    private VoucherJdbcRepository voucherJdbcRepository;
//
//    @Autowired
//    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
//
//    @BeforeEach
//    void setUp() {
//        VoucherJdbcReader reader = new VoucherJdbcReader(namedParameterJdbcTemplate, new VoucherReaderRowMapperManager());
//        VoucherJdbcStore store = new VoucherJdbcStore(namedParameterJdbcTemplate);
//        voucherJdbcRepository = new VoucherJdbcRepository(reader, store);
//    }
//
//    @Test
//    @DisplayName("저장 시_저장되어 있지 않은 Voucher라면_Voucher가 저장된다.")
//    void save_UnsavedVoucher_VoucherSaved() {
//        // given
//        Voucher voucher = new FixedAmountVoucher(
//                UUID.randomUUID(), new FixedAmountValue(50.0));
//
//        // when
//        voucherJdbcRepository.save(voucher);
//        var result = voucherJdbcRepository.findById(voucher.getUuid());
//
//        // then
//        assertThat(result).isPresent();
//        assertThat(result.get().getUuid()).isEqualTo(voucher.getUuid());
//        assertThat(result.get().getSimpleName()).isEqualTo(voucher.getClass().getSimpleName());
//        assertThat(result.get().getDiscountValue()).isEqualTo(voucher.getDiscountValue());
//    }
//
//    @Test
//    @DisplayName("findById_저장된 Voucher라면_해당 Voucher를 반환한다.")
//    void findById_savedVoucher_ReturnsVoucher() {
//        // given
//        Voucher voucher = new FixedAmountVoucher(
//                UUID.randomUUID(), new FixedAmountValue(50.0));
//        voucherJdbcRepository.save(voucher);
//
//        // when
//        Optional<VoucherCreateResultSet> foundVoucher = voucherJdbcRepository.findById(
//                voucher.getUuid());
//
//        // then
//        assertThat(foundVoucher).isPresent();
//        assertThat(foundVoucher.get().getUuid()).isEqualTo(voucher.getUuid());
//    }
//
//    @Test
//    @DisplayName("findById_저장되지 않은 Voucher_Empty를 반환한다.")
//    void findById_UnsavedVoucher_ReturnsEmpty() {
//        // given
//        UUID nonExistentId = UUID.randomUUID();
//
//        // when
//        Optional<VoucherCreateResultSet> foundVoucher = voucherJdbcRepository.findById(
//                nonExistentId);
//
//        // then
//        assertThat(foundVoucher).isNotPresent();
//    }
//
//    @Test
//    @DisplayName("findAll_저장된 Vouchers_저장된 모든 Vouchers들을 반환한다.")
//    void findAll_SavedVouchers_ReturnsAllVouchers() {
//        // given
//        Voucher voucher1 = new FixedAmountVoucher(
//                UUID.randomUUID(), new FixedAmountValue(50.0));
//        Voucher voucher2 = new PercentageVoucher(
//                UUID.randomUUID(), new PercentageDiscountValue(30.0));
//        voucherJdbcRepository.save(voucher1);
//        voucherJdbcRepository.save(voucher2);
//
//        // when
//        List<VoucherCreateResultSet> allVouchers = voucherJdbcRepository.findAll();
//
//        // then
//        assertThat(allVouchers).hasSize(2);
//        assertThat(allVouchers).extracting("uuid").contains(voucher1.getUuid(), voucher2.getUuid());
//    }
//
//    @Test
//    @DisplayName("deleteById_저장된 Vocuher_Voucher를 제거한다.")
//    void deleteById_SavedVoucher_VoucherDeleted() {
//        // given
//        Voucher voucher = new FixedAmountVoucher(
//                UUID.randomUUID(), new FixedAmountValue(50.0));
//        voucherJdbcRepository.save(voucher);
//
//        // then
//        voucherJdbcRepository.deleteById(voucher.getUuid());
//        Optional<VoucherCreateResultSet> foundVoucher = voucherJdbcRepository.findById(
//                voucher.getUuid());
//
//        // when
//        assertThat(foundVoucher).isNotPresent();
//    }
//
//    @Test
//    @DisplayName("deleteAll_저장된 모든 Voucher_모든 Voucher를 제거한다.")
//    void deleteAll_SavedMultipleVouchers_AllVouchersDeleted() {
//        // given
//        Voucher voucher1 = new FixedAmountVoucher(
//                UUID.randomUUID(), new FixedAmountValue(50.0));
//        Voucher voucher2 = new PercentageVoucher(
//                UUID.randomUUID(), new PercentageDiscountValue(30.0));
//        voucherJdbcRepository.save(voucher1);
//        voucherJdbcRepository.save(voucher2);
//
//        // then
//        voucherJdbcRepository.deleteAll();
//        List<VoucherCreateResultSet> allVouchers = voucherJdbcRepository.findAll();
//
//        // when
//        assertThat(allVouchers).isEmpty();
//    }
//}
