package org.prgms.kdt.application.voucher.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.kdt.application.customer.domain.Customer;
import org.prgms.kdt.application.customer.repository.CustomerRepository;
import org.prgms.kdt.application.voucher.domain.FixedAmountVoucher;
import org.prgms.kdt.application.voucher.domain.PercentDiscountVoucher;
import org.prgms.kdt.application.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class JdbcVoucherRepositoryTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    Customer customer;
    Voucher voucher;

    @BeforeEach
    void beforeEach() {
        customer = new Customer(
            UUID.randomUUID(),
            "sample3",
            "sample3@gmail.com",
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
    @DisplayName("특정 고객에게 voucher insert 성공")
    void insert() {
        Voucher newVoucher = new PercentDiscountVoucher(
            UUID.randomUUID(),
            customer.getCustomerId(),
            10L,
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        voucherRepository.insert(newVoucher);
        Optional<Voucher> findVoucher = voucherRepository.findByVoucherId(newVoucher.getVoucherId());
        assertThat(newVoucher).isEqualTo(findVoucher.get());
    }

    @Test
    @DisplayName("할당된 고객이 없을 때 voucher insert 성공")
    void customerNullVoucherInsert() {
        Voucher newVoucher = new PercentDiscountVoucher(
            UUID.randomUUID(),
            null,
            10L,
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        voucherRepository.insert(newVoucher);
        Optional<Voucher> findVoucher = voucherRepository.findByVoucherId(newVoucher.getVoucherId());
        assertThat(newVoucher).isEqualTo(findVoucher.get());
    }

    @Test
    @DisplayName("모든 voucher 조회")
    void findAll() {
        List<Voucher> customerList = voucherRepository.findAll();
        assertThat(customerList.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("고객 ID를 통해서 고객이 가지고 있는 Voucher 조회")
    void findByCustomerId() {
        Voucher newVoucher1 = new PercentDiscountVoucher(UUID.randomUUID(), customer.getCustomerId(), 10L, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        Voucher newVoucher2 = new FixedAmountVoucher(UUID.randomUUID(), customer.getCustomerId(), 2000L, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        voucherRepository.insert(newVoucher1);
        voucherRepository.insert(newVoucher2);
        List<Voucher> voucherList = voucherRepository.findByCustomerId(customer.getCustomerId());
        assertThat(voucherList.size()).isSameAs(3);
    }
    
    @Test
    @DisplayName("discountValue 수정")
    void update() {
        voucher.changeDiscountValue(3000L);
        voucherRepository.updateDiscountValue(this.voucher);
        Optional<Voucher> findVoucher = voucherRepository.findByVoucherId(voucher.getVoucherId());
        assertThat(findVoucher.get().getDiscountValue()).isEqualTo(3000L);
    }

    @Test
    @DisplayName("voucherId를 통해서 voucher 삭제")
    void deleteById() {
        int delete = voucherRepository.deleteById(voucher.getVoucherId());
        Optional<Voucher> findVoucher = voucherRepository.findByVoucherId(voucher.getVoucherId());
        assertThat(findVoucher.isEmpty()).isTrue();
    }
}