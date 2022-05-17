package org.programmers.kdtspring.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.kdtspring.dto.CreateVoucherRequest;
import org.programmers.kdtspring.entity.user.Customer;
import org.programmers.kdtspring.entity.voucher.FixedAmountVoucher;
import org.programmers.kdtspring.entity.voucher.PercentDiscountVoucher;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.entity.voucher.VoucherType;
import org.programmers.kdtspring.repository.user.CustomerRepository;
import org.programmers.kdtspring.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class VoucherServiceTest {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VoucherService voucherService;

    @AfterEach
    void clean() {
        voucherRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처 생성 테스트")
    void testCreateVoucher() {
        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest();
        createVoucherRequest.setVoucherType(VoucherType.FixedAmountVoucher);
        createVoucherRequest.setAmount(10000);

        UUID voucherId = voucherService.createVoucher(createVoucherRequest.getVoucherType().name(), createVoucherRequest.getAmount());

        assertThat(voucherId).isNotNull();
    }

    @Test
    @DisplayName("바우처 전체 조회")
    void testGetVouchers() {
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), null, 10000, VoucherType.FixedAmountVoucher.name());
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), null, 20000, VoucherType.FixedAmountVoucher.name());
        Voucher voucher3 = new PercentDiscountVoucher(UUID.randomUUID(), null, 20000, VoucherType.PercentDiscountVoucher.name());
        Voucher voucher4 = new PercentDiscountVoucher(UUID.randomUUID(), null, 20000, VoucherType.PercentDiscountVoucher.name());
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);
        voucherRepository.insert(voucher4);

        List<Voucher> vouchers = voucherService.getVouchers();

        assertThat(vouchers.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("바우처 타입으로 조회")
    void testGetVouchersByType() {
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), null, 10000, VoucherType.FixedAmountVoucher.name());
        Voucher voucher2 = new FixedAmountVoucher(UUID.randomUUID(), null, 20000, VoucherType.FixedAmountVoucher.name());
        Voucher voucher3 = new PercentDiscountVoucher(UUID.randomUUID(), null, 20000, VoucherType.PercentDiscountVoucher.name());
        Voucher voucher4 = new PercentDiscountVoucher(UUID.randomUUID(), null, 20000, VoucherType.PercentDiscountVoucher.name());
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);
        voucherRepository.insert(voucher4);

        List<Voucher> fixedVouchers = voucherService.getVouchersByType(VoucherType.FixedAmountVoucher.name());
        List<Voucher> percentVouchers = voucherService.getVouchersByType(VoucherType.PercentDiscountVoucher.name());

        assertAll(
                () -> assertThat(fixedVouchers.size()).isEqualTo(2),
                () -> assertThat(percentVouchers.size()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("바우처 ID로 단건 조회")
    void testGetVoucher() {
        UUID voucherId = UUID.randomUUID();
        voucherRepository.insert(new FixedAmountVoucher(voucherId, null, 1000, VoucherType.FixedAmountVoucher.name()));

        Optional<Voucher> voucher = voucherService.getVoucher(voucherId);

        assertThat(voucher.isPresent()).isTrue();
    }

    @Test
    @DisplayName("바우처 고객에게 할당")
    void testAllocateVoucher() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), null, 10000, VoucherType.FixedAmountVoucher.name());
        voucherRepository.insert(voucher);
        Customer customer = new Customer(UUID.randomUUID(), "jiwoong", "iop1996@naver.com", LocalDateTime.now());
        customerRepository.insert(customer);

        Voucher updatedVoucher = voucherService.allocateVoucher(voucher.getVoucherId(), customer.getCustomerId());

        assertThat(updatedVoucher.getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @Test
    @DisplayName("한 고객에게 할당된 바우처 조회")
    void findVoucherForCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), "jiwoong", "iop1996@naver.com", LocalDateTime.now());
        customerRepository.insert(customer);
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), customer.getCustomerId(), 10000, VoucherType.FixedAmountVoucher.name());
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), customer.getCustomerId(), 10, VoucherType.PercentDiscountVoucher.name());
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);

        List<Voucher> voucherForCustomer = voucherService.findVoucherForCustomer(customer.getCustomerId());

        assertThat(voucherForCustomer.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("바우처 단건 삭제")
    void testRemoveVoucher() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), null, 10000, VoucherType.FixedAmountVoucher.name());
        voucherRepository.insert(voucher);

        voucherService.removeVoucher(voucher.getVoucherId());

        assertThat(voucherRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("바우처 전체 삭제")
    void testRemoveAllVoucher() {
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), null, 10000, VoucherType.FixedAmountVoucher.name());
        voucherRepository.insert(voucher);

        voucherService.removeAllVoucher();

        assertThat(voucherRepository.findAll()).isEmpty();
    }
}