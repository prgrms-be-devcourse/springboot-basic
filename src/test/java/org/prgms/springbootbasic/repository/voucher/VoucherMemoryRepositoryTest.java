package org.prgms.springbootbasic.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.domain.voucher.FixedAmountPolicy;
import org.prgms.springbootbasic.domain.voucher.PercentDiscountPolicy;
import org.prgms.springbootbasic.domain.voucher.Voucher;
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
        Voucher fixedVoucher = new Voucher(voucherId, 1000, new FixedAmountPolicy());

        voucherMemoryRepository.upsert(fixedVoucher);

        Optional<Voucher> retrievedVoucher = voucherMemoryRepository.findById(voucherId);
        Optional<Voucher> randomRetrievedVoucher = voucherMemoryRepository.findById(UUID.randomUUID());

        assertThat(randomRetrievedVoucher.isEmpty(), is(true));
        assertThat(retrievedVoucher.isEmpty(), is(false));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(fixedVoucher));
    }

    @Test
    void findAllVoucherFromMemory() {
        Voucher percentDiscountVoucher = new Voucher(UUID.randomUUID(), 20, new PercentDiscountPolicy());
        Voucher fixedVoucher = new Voucher(UUID.randomUUID(), 6000, new FixedAmountPolicy());

        voucherMemoryRepository.upsert(percentDiscountVoucher);
        voucherMemoryRepository.upsert(fixedVoucher);

        assertThat(voucherMemoryRepository.findAll(), hasSize(2));
        assertThat(voucherMemoryRepository.findAll(), hasItem(samePropertyValuesAs(percentDiscountVoucher)));
        assertThat(voucherMemoryRepository.findAll(), hasItem(samePropertyValuesAs(fixedVoucher)));
    }

    @Test
    void createNewVouchersToMemory() {
        Voucher fixedVoucher = new Voucher(UUID.randomUUID(), 1000, new FixedAmountPolicy());
        Voucher percentDiscountVoucher = new Voucher(UUID.randomUUID(), 30, new PercentDiscountPolicy());

        this.voucherMemoryRepository.upsert(fixedVoucher);
        this.voucherMemoryRepository.upsert(percentDiscountVoucher);

        assertThat(voucherMemoryRepository.findAll(), hasSize(2));
        assertThrows(IllegalArgumentException.class,
                () -> voucherMemoryRepository.upsert(new Voucher(UUID.randomUUID(), 0, new PercentDiscountPolicy()))); // 여기에 이걸 넣는게 맞나?
    }

    @Test
    void deleteAllVouchersFromMemory() {
        voucherMemoryRepository.upsert(new Voucher(UUID.randomUUID(), 10, new PercentDiscountPolicy()));
        voucherMemoryRepository.upsert(new Voucher(UUID.randomUUID(), 20, new PercentDiscountPolicy()));
        voucherMemoryRepository.upsert(new Voucher(UUID.randomUUID(), 1000, new FixedAmountPolicy()));

        voucherMemoryRepository.deleteAll();

        assertThat(voucherMemoryRepository.findAll(), hasSize(0));
    }
}
