package org.prgrms.kdt.service.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.model.voucher.FixedAmountVoucher;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class VoucherServiceTest {

    @Container
    private static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:8.0.26")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("root1234!");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
    }

    Voucher newVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100L);
    VoucherType newVoucherType = VoucherType.FIXED_AMOUNT;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherService voucherService;

    @BeforeEach
    void setup() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처가 생성된다.")
    void testCreate() {
        voucherService.create(newVoucher.getVoucherId(), newVoucher.getVoucherValue(), newVoucherType);
        List<Voucher> vouchers = voucherService.findAll();
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers.size(), is(1));
        assertThat(vouchers.get(0), samePropertyValuesAs(newVoucher));
    }

    @Test
    @DisplayName("바우처 생성에 실패한다.")
    void testError_Create() {
        assertThrows(IllegalArgumentException.class, () -> {
            voucherService.create(null, newVoucher.getVoucherValue(), newVoucherType);
            voucherService.create(UUID.randomUUID(), 0, VoucherType.FIXED_AMOUNT);
            voucherService.create(UUID.randomUUID(), 0, VoucherType.PERCENT_DISCOUNT);
            voucherService.create(UUID.randomUUID(), 101, VoucherType.PERCENT_DISCOUNT);
            voucherService.create(UUID.randomUUID(), 101, null);
            voucherService.create(null, 101, null);
        });
    }

    @Test
    @DisplayName("바우처가 수정된다.")
    void testUpdate() {
        // given
        long updatedValue = 1L;

        Voucher voucher = voucherService.create(newVoucher.getVoucherId(), newVoucher.getVoucherValue(), VoucherType.FIXED_AMOUNT);

        // when
        voucherService.update(newVoucher.getVoucherId(), updatedValue);

        // then
        Voucher updatedVoucher = voucherRepository.findById(newVoucher.getVoucherId())
            .orElseThrow(() -> new IllegalArgumentException("Could not found voucher with voucherId=" + voucher.getVoucherId()));

        assertThat(updatedVoucher.getVoucherValue(), is(updatedValue));
    }

    @Test
    @DisplayName("바우처 수정에 실패한다.")
    void testError_Update() {
        Voucher fixedVoucher = voucherService.create(newVoucher.getVoucherId(), newVoucher.getVoucherValue(), VoucherType.FIXED_AMOUNT);
        Voucher percentVoucher = voucherService.create(UUID.randomUUID(), 1L, VoucherType.PERCENT_DISCOUNT);

        assertThrows(IllegalArgumentException.class, () -> {
            voucherService.update(null, 1L);
            voucherService.update(UUID.randomUUID(), 1L);
            voucherService.update(fixedVoucher.getVoucherId(), 0L);
            voucherService.update(percentVoucher.getVoucherId(), 0L);
            voucherService.update(percentVoucher.getVoucherId(), 101L);
        });
    }

    @Test
    @DisplayName("바우처가 삭제된다.")
    void testDelete() {
        // given
        voucherService.create(newVoucher.getVoucherId(), newVoucher.getVoucherValue(), VoucherType.FIXED_AMOUNT);
        List<Voucher> vouchers = voucherService.findAll();
        assertThat(vouchers.isEmpty(), is(false));
        assertThat(vouchers.size(), is(1));

        // when
        voucherService.delete(newVoucher.getVoucherId());

        // then
        List<Voucher> afterVouchers = voucherService.findAll();
        assertThat(afterVouchers.isEmpty(), is(true));
    }

    @Test
    @DisplayName("바우처가 삭제 실패한다.")
    void testError_Delete() {
        assertThrows(IllegalArgumentException.class, () -> {
            voucherService.delete(null);
            voucherService.delete(UUID.randomUUID());
        });
    }
}