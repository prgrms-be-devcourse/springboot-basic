package devcourse.springbootbasic.repository.voucher;

import devcourse.springbootbasic.JdbcRepositoryTest;
import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.repository.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static devcourse.springbootbasic.TestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

class JdbcVoucherRepositoryTest extends JdbcRepositoryTest {

    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        String query = """
                DELETE FROM voucher
                """;
        jdbcTemplate.update(query, (SqlParameterSource) null);
    }

    @Test
    @DisplayName("모든 Voucher를 조회할 수 있습니다.")
    void testFindAllVouchers() {
        // Given
        Voucher voucher1 = generateUnassignedVoucher(VoucherType.FIXED, 10);
        Voucher voucher2 = generateUnassignedVoucher(VoucherType.PERCENT, 20);
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        // When
        List<Voucher> vouchers = voucherRepository.findAll();

        // Then
        assertThat(vouchers).hasSize(2);
    }

    @ParameterizedTest
    @MethodSource("devcourse.springbootbasic.TestDataFactory#searchConditionProvider")
    @DisplayName("바우처 생성기간 및 특정 할인타입별 바우처를 조회할 수 있습니다.")
    void testFindAllVoucherWithSearchConditions(VoucherType voucherType, LocalDate startDate, LocalDate endDate, int expectedSize) {
        // Given
        List<Voucher> voucherList = generateSampleVouchers();

        for (Voucher voucher : voucherList) {
            voucherRepository.save(voucher);
        }

        // When
        List<Voucher> vouchers = voucherRepository.findAllWithFilter(voucherType, startDate, endDate);

        // Then
        assertThat(vouchers).hasSize(expectedSize);
    }

    @Test
    @DisplayName("Voucher를 ID로 조회할 수 있습니다.")
    void testFindVoucherById() {
        // Given
        Voucher voucher = generateUnassignedVoucher(VoucherType.FIXED, 15);
        UUID voucherId = voucher.getId();
        voucherRepository.save(voucher);

        // When
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);

        // Then
        assertThat(foundVoucher).isPresent();
        assertThat(foundVoucher.get().getId()).isEqualTo(voucherId);
    }

    @Test
    @DisplayName("Voucher를 수정할 수 있습니다.")
    void testUpdateVoucher() {
        // Given
        Voucher voucher = generateUnassignedVoucher(VoucherType.FIXED, 15);
        UUID voucherId = voucher.getId();
        voucherRepository.save(voucher);

        // When
        Voucher updatedVoucher = voucher.updateDiscountValue(20);
        boolean updateResult = voucherRepository.update(updatedVoucher);

        // Then
        assertThat(updateResult).isTrue();
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);
        assertThat(foundVoucher).isPresent();
        assertThat(foundVoucher.get().getDiscountValue()).isEqualTo(20);
    }

    @Test
    @DisplayName("수정할 Voucher가 없으면 false를 반환합니다.")
    void testUpdateVoucherFail() {
        // Given
        Voucher voucher = generateUnassignedVoucher(VoucherType.FIXED, 15);

        // When
        boolean updateResult = voucherRepository.update(voucher);

        // Then
        assertThat(updateResult).isFalse();
    }

    @Test
    @DisplayName("Voucher를 삭제할 수 있습니다.")
    void testDeleteVoucher() {
        // Given
        Voucher voucher = generateUnassignedVoucher(VoucherType.FIXED, 15);
        UUID voucherId = voucher.getId();
        voucherRepository.save(voucher);

        // When
        voucherRepository.delete(voucher);

        // Then
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);
        assertThat(foundVoucher).isEmpty();
    }

    @Test
    @DisplayName("Customer ID로 Voucher를 조회할 수 있습니다.")
    void testFindByCustomerId() {
        // Given
        Customer customer = generateNotBlacklistCustomers("Customer 1");
        UUID customerId = customer.getId();
        customerRepository.save(customer);

        Voucher voucher1 = generateAssignedVoucher(VoucherType.FIXED, 10, customerId);
        Voucher voucher2 = generateAssignedVoucher(VoucherType.FIXED, 20, customerId);

        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        // When
        List<Voucher> vouchers = voucherRepository.findByCustomerId(customerId);

        // Then
        assertThat(vouchers).hasSize(2);
    }

    @Test
    @DisplayName("Voucher를 저장할 수 있습니다.")
    void testSaveVoucher() {
        // Given
        Voucher voucher = generateUnassignedVoucher(VoucherType.FIXED, 15);
        UUID voucherId = voucher.getId();

        // When
        Voucher savedVoucher = voucherRepository.save(voucher);

        // Then
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);
        assertThat(foundVoucher).isPresent();
        assertThat(foundVoucher.get().getId()).isEqualTo(savedVoucher.getId());
        assertThat(foundVoucher.get().getVoucherType()).isEqualTo(savedVoucher.getVoucherType());
        assertThat(foundVoucher.get().getDiscountValue()).isEqualTo(savedVoucher.getDiscountValue());
        assertThat(foundVoucher.get().getCustomerId()).isEqualTo(savedVoucher.getCustomerId());
    }
}
