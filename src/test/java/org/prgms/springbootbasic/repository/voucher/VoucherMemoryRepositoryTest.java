package org.prgms.springbootbasic.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@SpringJUnitConfig(VoucherMemoryRepository.class)
@ActiveProfiles("local")
class VoucherMemoryRepositoryTest {

    @Autowired
    private VoucherMemoryRepository voucherMemoryRepository;

    @BeforeEach
    void cleanUp(){
        voucherMemoryRepository.deleteAll();
    }

    @Test
    void findVoucherByIdFromMemory() {
        UUID voucherId = UUID.randomUUID();
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 1000);

        voucherMemoryRepository.save(fixedAmountVoucher);

        Optional<VoucherPolicy> retrievedVoucher = voucherMemoryRepository.findById(voucherId);
        Optional<VoucherPolicy> randomRetrievedVoucher = voucherMemoryRepository.findById(UUID.randomUUID());

        assertThat(randomRetrievedVoucher.isEmpty(), is(true));
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    void findAllVoucherFromMemory() {
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 6000);

        voucherMemoryRepository.save(percentDiscountVoucher);
        voucherMemoryRepository.save(fixedAmountVoucher);

        assertThat(voucherMemoryRepository.findAll(), hasSize(2));
        assertThat(voucherMemoryRepository.findAll(), hasItem(samePropertyValuesAs(percentDiscountVoucher)));
        assertThat(voucherMemoryRepository.findAll(), hasItem(samePropertyValuesAs(fixedAmountVoucher)));
    }

    @Test
    void createNewVouchersToMemory() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30);

        this.voucherMemoryRepository.save(fixedAmountVoucher);
        this.voucherMemoryRepository.save(percentDiscountVoucher);

        assertThat(voucherMemoryRepository.findAll(), hasSize(2));
        assertThrows(IllegalArgumentException.class,
                () -> voucherMemoryRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 0)));
    }

    @Test
    void deleteAllVouchersFromMemory() {
        voucherMemoryRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 10));
        voucherMemoryRepository.save(new PercentDiscountVoucher(UUID.randomUUID(), 20));
        voucherMemoryRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 1000));

        voucherMemoryRepository.deleteAll();

        assertThat(voucherMemoryRepository.findAll(), hasSize(0));
    }
}
