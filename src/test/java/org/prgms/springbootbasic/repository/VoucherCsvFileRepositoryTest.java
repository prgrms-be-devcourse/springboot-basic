package org.prgms.springbootbasic.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.domain.voucher.FixedAmountVoucher;
import org.prgms.springbootbasic.domain.voucher.PercentDiscountVoucher;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("dev")
@SpringBootTest
class VoucherCsvFileRepositoryTest {

    @Autowired
    private VoucherCsvFileRepository voucherCsvFileRepository;

    @BeforeEach
    void cleanUp(){
        voucherCsvFileRepository.deleteAll();
    }

    @Test
    void findVoucherByIdFromFile() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        voucherCsvFileRepository.create(fixedAmountVoucher);

        Optional<VoucherPolicy> retrievedVoucher = voucherCsvFileRepository.findById(fixedAmountVoucher.getVoucherId());

        assertThat(retrievedVoucher.isPresent(), is(true));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    void findAllVoucherFromFile() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);

        voucherCsvFileRepository.create(fixedAmountVoucher);
        voucherCsvFileRepository.create(percentDiscountVoucher);

        assertThat(voucherCsvFileRepository.findAll(), hasSize(2));
        assertThat(voucherCsvFileRepository.findAll(), hasItem(fixedAmountVoucher));
        assertThat(voucherCsvFileRepository.findAll(), hasItem(percentDiscountVoucher));
    }

    @Test
    void createVoucherToFile() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 2000);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);

        voucherCsvFileRepository.create(fixedAmountVoucher);
        voucherCsvFileRepository.create(percentDiscountVoucher);

        assertThat(voucherCsvFileRepository.findAll(), hasSize(2));
        assertThat(voucherCsvFileRepository.findAll(), hasItem(fixedAmountVoucher));
        assertThat(voucherCsvFileRepository.findAll(), hasItem(percentDiscountVoucher));
        assertThat(voucherCsvFileRepository.findAll(),
                not(hasItem(new FixedAmountVoucher(UUID.randomUUID(), 2000))));
    }

    @Test
    void deleteVoucherById() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        voucherCsvFileRepository.create(fixedAmountVoucher);

        assertThat(voucherCsvFileRepository.findById(fixedAmountVoucher.getVoucherId()).isPresent(), is(true));

        voucherCsvFileRepository.deleteById(UUID.randomUUID());
        assertThat(voucherCsvFileRepository.findAll(), hasSize(1));

        voucherCsvFileRepository.deleteById(fixedAmountVoucher.getVoucherId());
        assertThat(voucherCsvFileRepository.findAll(), hasSize(0));
    }

    @Test
    void deleteAllVoucher() {
        voucherCsvFileRepository.create(new FixedAmountVoucher(UUID.randomUUID(), 1000));

        voucherCsvFileRepository.deleteAll();

        assertThat(voucherCsvFileRepository.findAll(), hasSize(0));
    }
}
