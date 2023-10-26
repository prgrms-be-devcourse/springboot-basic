package devcourse.springbootbasic.repository.voucher;

import devcourse.springbootbasic.domain.customer.Customer;
import devcourse.springbootbasic.domain.voucher.Voucher;
import devcourse.springbootbasic.domain.voucher.VoucherType;
import devcourse.springbootbasic.repository.customer.CustomerRepository;
import devcourse.springbootbasic.util.UUIDUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class JdbcVoucherRepositoryTest {

    @Autowired
    private JdbcVoucherRepository voucherRepository;
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
        Voucher voucher1 = Voucher.createVoucher(UUIDUtil.generateRandomUUID(), VoucherType.FIXED, 10, null);
        Voucher voucher2 = Voucher.createVoucher(UUIDUtil.generateRandomUUID(), VoucherType.PERCENT, 20, null);
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);

        // When
        List<Voucher> vouchers = voucherRepository.findAll();

        // Then
        assertThat(vouchers).hasSize(2);
    }

    @Test
    @DisplayName("Voucher를 ID로 조회할 수 있습니다.")
    void testFindVoucherById() {
        // Given
        UUID voucherId = UUIDUtil.generateRandomUUID();
        Voucher voucher = Voucher.createVoucher(voucherId, VoucherType.FIXED, 15, null);
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
        UUID voucherId = UUIDUtil.generateRandomUUID();
        Voucher voucher = Voucher.createVoucher(voucherId, VoucherType.FIXED, 15, null);
        voucherRepository.save(voucher);

        // When
        Voucher updatedVoucher = voucher.updateDiscountValue(20);
        int updatedRows = voucherRepository.update(updatedVoucher);

        // Then
        assertThat(updatedRows).isEqualTo(1);
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);
        assertThat(foundVoucher).isPresent();
        assertThat(foundVoucher.get().getDiscountValue()).isEqualTo(20);
    }

    @Test
    @DisplayName("수정할 Voucher가 없으면 0을 반환합니다.")
    void testUpdateVoucherFail() {
        // Given
        UUID voucherId = UUIDUtil.generateRandomUUID();
        Voucher voucher = Voucher.createVoucher(voucherId, VoucherType.FIXED, 15, null);

        // When
        int updatedRows = voucherRepository.update(voucher);

        // Then
        assertThat(updatedRows).isZero();
    }

    @Test
    @DisplayName("Voucher를 삭제할 수 있습니다.")
    void testDeleteVoucher() {
        // Given
        UUID voucherId = UUIDUtil.generateRandomUUID();
        Voucher voucher = Voucher.createVoucher(voucherId, VoucherType.FIXED, 15, null);
        voucherRepository.save(voucher);

        // When
        int deletedRows = voucherRepository.delete(voucher);

        // Then
        assertThat(deletedRows).isEqualTo(1);
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);
        assertThat(foundVoucher).isEmpty();
    }

    @Test
    @DisplayName("삭제할 Voucher가 없으면 0을 반환합니다.")
    void testDeleteVoucherFail() {
        // Given
        UUID voucherId = UUIDUtil.generateRandomUUID();
        Voucher voucher = Voucher.createVoucher(voucherId, VoucherType.FIXED, 15, null);

        // When
        int deletedRows = voucherRepository.delete(voucher);

        // Then
        assertThat(deletedRows).isZero();
    }

    @Test
    @DisplayName("Customer ID로 Voucher를 조회할 수 있습니다.")
    void testFindByCustomerId() {
        // Given
        UUID customerId = UUIDUtil.generateRandomUUID();
        customerRepository.save(Customer.createCustomer(customerId, "Customer 1", false));
        Voucher voucher1 = Voucher.createVoucher(UUIDUtil.generateRandomUUID(), VoucherType.FIXED, 10, customerId);
        Voucher voucher2 = Voucher.createVoucher(UUIDUtil.generateRandomUUID(), VoucherType.FIXED, 20, customerId);
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
        UUID voucherId = UUIDUtil.generateRandomUUID();
        Voucher voucher = Voucher.createVoucher(voucherId, VoucherType.FIXED, 15, null);

        // When
        Voucher savedVoucher = voucherRepository.save(voucher);

        // Then
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);
        assertThat(foundVoucher)
                .isPresent()
                .contains(savedVoucher);
    }
}
