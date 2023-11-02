package org.prgms.springbootbasic.repository.voucher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgms.springbootbasic.domain.voucher.FixedAmountPolicy;
import org.prgms.springbootbasic.domain.voucher.PercentDiscountPolicy;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ActiveProfiles("test")
class VoucherCsvFileRepositoryTest {

    @Autowired
    private VoucherCsvFileRepository voucherCsvFileRepository;

    @BeforeEach
    void cleanUp(){
        voucherCsvFileRepository.deleteAll();
    }

    @Test
    void findVoucherByIdFromFile() {
        Voucher fixedVoucher = new Voucher(UUID.randomUUID(), 1000, new FixedAmountPolicy());

        voucherCsvFileRepository.upsert(fixedVoucher);

        Optional<Voucher> retrievedVoucher = voucherCsvFileRepository.findById(fixedVoucher.getVoucherId());

        assertThat(retrievedVoucher.isPresent(), is(true));
        compareVoucher(retrievedVoucher.get(), fixedVoucher);
    }

    @Test
    void findAllVoucherFromFile() {
        Voucher fixedVoucher = new Voucher(UUID.randomUUID(), 1000, new FixedAmountPolicy());
        Voucher percentVoucher = new Voucher(UUID.randomUUID(), 10, new PercentDiscountPolicy());

        voucherCsvFileRepository.upsert(fixedVoucher);
        voucherCsvFileRepository.upsert(percentVoucher);

        List<Voucher> vouchers = voucherCsvFileRepository.findAll();
        List<UUID> voucherUUIDs = vouchers.stream().map(Voucher::getVoucherId).toList();

        assertThat(vouchers, hasSize(2));
        assertThat(voucherUUIDs, hasItem(fixedVoucher.getVoucherId()));
        assertThat(voucherUUIDs, hasItem(percentVoucher.getVoucherId()));
    }

    @Test
    void createVoucherToFile() {
        Voucher fixedVoucher = new Voucher(UUID.randomUUID(), 2000, new FixedAmountPolicy());
        Voucher percentVoucher = new Voucher(UUID.randomUUID(), 20, new PercentDiscountPolicy());

        voucherCsvFileRepository.upsert(fixedVoucher);
        voucherCsvFileRepository.upsert(percentVoucher);

        List<Voucher> vouchers = voucherCsvFileRepository.findAll();
        List<UUID> voucherUUIDs = vouchers.stream().map(Voucher::getVoucherId).toList();

        assertThat(vouchers, hasSize(2));
        assertThat(voucherUUIDs, hasItem(fixedVoucher.getVoucherId()));
        assertThat(voucherUUIDs, hasItem(percentVoucher.getVoucherId()));
        assertThat(vouchers,
                not(hasItem(samePropertyValuesAs(new Voucher(UUID.randomUUID(), 2000, new FixedAmountPolicy())))));
    }

    @Test
    void deleteVoucherById() {
        Voucher fixedVoucher = new Voucher(UUID.randomUUID(), 1000, new FixedAmountPolicy());

        voucherCsvFileRepository.upsert(fixedVoucher);

        assertThat(voucherCsvFileRepository.findById(fixedVoucher.getVoucherId()).isPresent(), is(true));

        voucherCsvFileRepository.deleteById(UUID.randomUUID());
        assertThat(voucherCsvFileRepository.findAll(), hasSize(1));

        voucherCsvFileRepository.deleteById(fixedVoucher.getVoucherId());
        assertThat(voucherCsvFileRepository.findAll(), hasSize(0));
    }

    @Test
    void deleteAllVoucher() {
        voucherCsvFileRepository.upsert(new Voucher(UUID.randomUUID(), 1000, new FixedAmountPolicy()));

        voucherCsvFileRepository.deleteAll();

        assertThat(voucherCsvFileRepository.findAll(), hasSize(0));
    }

    private void compareVoucher(Voucher v1, Voucher v2){
        assertThat(v1.getVoucherId(), is(v2.getVoucherId()));
        assertThat(v1.getDiscountDegree(), is(v2.getDiscountDegree()));
        assertThat(v1.getVoucherPolicy().getClass().getSimpleName(), is(v2.getVoucherPolicy().getClass().getSimpleName()));
    }
}
