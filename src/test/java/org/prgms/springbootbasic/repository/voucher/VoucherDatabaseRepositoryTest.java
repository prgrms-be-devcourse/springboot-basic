package org.prgms.springbootbasic.repository.voucher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.domain.voucher.FixedAmountVoucher;
import org.prgms.springbootbasic.domain.voucher.PercentDiscountVoucher;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@ActiveProfiles("dev")
class VoucherDatabaseRepositoryTest {
    @Autowired
    private VoucherDatabaseRepository voucherDatabaseRepository;

    private PercentDiscountVoucher setUpVoucher;
    @BeforeEach
    void setUp() {
        setUpVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 40);
        voucherDatabaseRepository.upsert(setUpVoucher);
    }

    @AfterEach
    void clean() {
        voucherDatabaseRepository.deleteAll();
    }

    @Test
    void saveNewVoucherToDB() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);

        voucherDatabaseRepository.upsert(fixedAmountVoucher);

        Optional<VoucherPolicy> retrievedVoucher = voucherDatabaseRepository.findById(fixedAmountVoucher.getVoucherId());

        assertThat(retrievedVoucher.isPresent(), is(true));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(fixedAmountVoucher));
    }

    @Test
    void updateVoucherInDB() {
        FixedAmountVoucher updateVoucher = new FixedAmountVoucher(setUpVoucher.getVoucherId(), 2000);

        voucherDatabaseRepository.upsert(updateVoucher);

        Optional<VoucherPolicy> retrievedVoucher = voucherDatabaseRepository.findById(setUpVoucher.getVoucherId());

        assertThat(retrievedVoucher.isPresent(), is(true));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(updateVoucher));
    }

    @Test
    void findVoucherByIdInDB() {
        Optional<VoucherPolicy> retrievedVoucher = voucherDatabaseRepository.findById(setUpVoucher.getVoucherId());

        assertThat(retrievedVoucher.isPresent(), is(true));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(setUpVoucher));
    }

    @Test
    void findAllVouchersInDB() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 3000);

        voucherDatabaseRepository.upsert(fixedAmountVoucher);

        List<VoucherPolicy> vouchers = voucherDatabaseRepository.findAll();

        assertThat(vouchers, hasSize(2));
        assertThat(vouchers, hasItem(samePropertyValuesAs(fixedAmountVoucher)));
        assertThat(vouchers, hasItem(samePropertyValuesAs(setUpVoucher)));
    }

    @Test
    void deleteVoucherByIdInDB() {
        voucherDatabaseRepository.deleteById(setUpVoucher.getVoucherId());

        List<VoucherPolicy> vouchers = voucherDatabaseRepository.findAll();

        assertThat(vouchers, hasSize(0));
    }

    @Test
    void deleteAllVouchersInDB() {
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 3000);

        voucherDatabaseRepository.upsert(fixedAmountVoucher);
        voucherDatabaseRepository.deleteAll();

        List<VoucherPolicy> vouchers = voucherDatabaseRepository.findAll();

        assertThat(vouchers.size(), is(0));
    }
}
