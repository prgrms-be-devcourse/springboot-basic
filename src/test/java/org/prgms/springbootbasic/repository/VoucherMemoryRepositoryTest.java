package org.prgms.springbootbasic.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.prgms.springbootbasic.BasicApplication;
import org.prgms.springbootbasic.domain.voucher.FixedAmountVoucher;
import org.prgms.springbootbasic.domain.voucher.PercentDiscountVoucher;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig(BasicApplication.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherMemoryRepositoryTest {

    @Autowired
    private VoucherMemoryRepository voucherMemoryRepository;

    @Test
    void findVoucherByIdFromMemory() {
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 1000);

        voucherMemoryRepository.create(fixedAmountVoucher);

        Optional<VoucherPolicy> retrievedVoucher = voucherMemoryRepository.findById(voucherId);
        Optional<VoucherPolicy> randomRetrievedVoucher = voucherMemoryRepository.findById(UUID.randomUUID());

        assertThat(randomRetrievedVoucher.isEmpty(), is(true));
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    @Order(1)
    void findAllVoucherFromMemory() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 6000);

        voucherMemoryRepository.create(percentDiscountVoucher);
        voucherMemoryRepository.create(fixedAmountVoucher);

        assertThat(voucherMemoryRepository.findAll(), hasSize(2));
        assertThat(voucherMemoryRepository.findAll(), hasItem(percentDiscountVoucher));
        assertThat(voucherMemoryRepository.findAll(), hasItem(fixedAmountVoucher));
    }

    @Test
    void createNewVouchersToMemory() {
        int beforeSize = voucherMemoryRepository.findAll().size();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30);

        this.voucherMemoryRepository.create(fixedAmountVoucher);
        this.voucherMemoryRepository.create(percentDiscountVoucher);

        assertThat(voucherMemoryRepository.findAll(), hasSize(2 + beforeSize));
        assertThrows(IllegalArgumentException.class,
                () -> voucherMemoryRepository.create(new PercentDiscountVoucher(UUID.randomUUID(), 0)));
    }
}
