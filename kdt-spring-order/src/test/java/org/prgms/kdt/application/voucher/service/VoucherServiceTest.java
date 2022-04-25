package org.prgms.kdt.application.voucher.service;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.kdt.application.customer.domain.Customer;
import org.prgms.kdt.application.customer.repository.CustomerRepository;
import org.prgms.kdt.application.voucher.domain.FixedAmountVoucher;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.prgms.kdt.application.voucher.domain.VoucherType;
import org.prgms.kdt.application.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VoucherServiceTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherService voucherService;

    Customer customer;
    Voucher voucher;

    @BeforeEach
    void beforeEach() {
        customer = new Customer(
            UUID.randomUUID(),
            "sample4",
            "sample4@gmail.com",
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        customerRepository.insert(customer);

        voucher = new FixedAmountVoucher(
            UUID.randomUUID(),
            customer.getCustomerId(),
            2000L,
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        voucherRepository.insert(voucher);
    }

    @AfterEach
    void dataCleanup() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("voucher 생성")
    void createVoucher() {
        Voucher newVoucher = voucherService.createVoucher(VoucherType.PERCENT_DISCOUNT, 20L, customer);
        Optional<Voucher> findVoucher = voucherService.getVoucherByVoucherId(newVoucher.getVoucherId());
        assertThat(newVoucher).isEqualTo(findVoucher.get());
    }

    @Test
    @DisplayName("모든 voucher 조회")
    void getAllVouchers() {
        List<Voucher> allVouchers = voucherService.getAllVouchers();
        assertThat(allVouchers).isNotEmpty();
    }

    @Test
    @DisplayName("voucherId로 voucher 조회, 존재하지 않는 voucherId로 조회시 exception 발생")
    void getVoucherByVoucherId() {
        Optional<Voucher> findVoucher = voucherService.getVoucherByVoucherId(voucher.getVoucherId());
        assertThat(findVoucher.get()).isEqualTo(voucher);
        assertThat(voucherService.getVoucherByVoucherId(UUID.randomUUID()).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("customerId로 voucher 조회")
    void getVoucherByCustomerId() {
        List<Voucher> voucherList = voucherService.getVoucherByCustomerId(customer.getCustomerId());
        assertThat(voucherList.size()).isEqualTo(1);
        assertThatThrownBy(() -> voucherService.getVoucherByCustomerId(UUID.randomUUID()))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("discountValue 수정 update")
    void updateVoucher() {
        voucherService.updateVoucher(voucher, 3000L);
        Optional<Voucher> findVoucher = voucherService.getVoucherByVoucherId(voucher.getVoucherId());
        assertThat(findVoucher.get().getDiscountValue()).isEqualTo(3000L);
    }

    @Test
    @DisplayName("voucherId로 voucher 삭제")
    void deleteVoucherId() {
        voucherService.deleteVoucherId(voucher.getVoucherId());
        assertThat(voucherService.getVoucherByVoucherId(voucher.getVoucherId()).isEmpty()).isTrue();
    }
}