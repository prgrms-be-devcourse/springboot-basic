package org.prgms.springbootbasic.repository.voucher;

import org.junit.jupiter.api.AfterEach;
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
@ActiveProfiles("dev")
class VoucherJdbcRepositoryTest {
    @Autowired
    private VoucherJdbcRepository voucherJdbcRepository;

    private Voucher setUpVoucher;
    @BeforeEach
    void setUp() {
        setUpVoucher = new Voucher(UUID.randomUUID(), 40, new PercentDiscountPolicy());
        voucherJdbcRepository.upsert(setUpVoucher);
    }

    @AfterEach
    void clean() {
        voucherJdbcRepository.deleteAll();
    }

    @Test
    void saveNewVoucherToDB() {
        Voucher fixedVoucher = new Voucher(UUID.randomUUID(), 1000, new FixedAmountPolicy());

        voucherJdbcRepository.upsert(fixedVoucher);

        Optional<Voucher> retrievedVoucher = voucherJdbcRepository.findById(fixedVoucher.getVoucherId());

        assertThat(retrievedVoucher.isPresent(), is(true));
        compareVoucher(retrievedVoucher.get(), fixedVoucher);
    }

    @Test
    void updateVoucherInDB() {
        Voucher updateVoucher = new Voucher(setUpVoucher.getVoucherId(), 2000, new FixedAmountPolicy());

        voucherJdbcRepository.upsert(updateVoucher);

        Optional<Voucher> retrievedVoucher = voucherJdbcRepository.findById(setUpVoucher.getVoucherId());

        assertThat(retrievedVoucher.isPresent(), is(true));
        compareVoucher(retrievedVoucher.get(), updateVoucher);
    }

    @Test
    void findVoucherByIdInDB() {
        Optional<Voucher> retrievedVoucher = voucherJdbcRepository.findById(setUpVoucher.getVoucherId());

        assertThat(retrievedVoucher.isPresent(), is(true));
        compareVoucher(retrievedVoucher.get(), setUpVoucher);
    }

    @Test
    void findAllVouchersInDB() {
        Voucher fixedVoucher = new Voucher(UUID.randomUUID(), 3000, new FixedAmountPolicy());

        voucherJdbcRepository.upsert(fixedVoucher);

        List<Voucher> vouchers = voucherJdbcRepository.findAll();
        List<UUID> voucherUUIDs = vouchers.stream().map(Voucher::getVoucherId).toList();

        assertThat(vouchers, hasSize(2));
        assertThat(voucherUUIDs, hasItem(fixedVoucher.getVoucherId()));
        assertThat(voucherUUIDs, hasItem(setUpVoucher.getVoucherId()));
    }

    @Test
    void deleteVoucherByIdInDB() {
        voucherJdbcRepository.deleteById(setUpVoucher.getVoucherId());

        List<Voucher> vouchers = voucherJdbcRepository.findAll();

        assertThat(vouchers, hasSize(0));
    }

    @Test
    void deleteAllVouchersInDB() {
        Voucher fixedVoucher = new Voucher(UUID.randomUUID(), 3000, new FixedAmountPolicy());

        voucherJdbcRepository.upsert(fixedVoucher);
        voucherJdbcRepository.deleteAll();

        List<Voucher> vouchers = voucherJdbcRepository.findAll();

        assertThat(vouchers.size(), is(0));
    }

    private void compareVoucher(Voucher v1, Voucher v2){
        assertThat(v1.getVoucherId(), is(v2.getVoucherId()));
        assertThat(v1.getDiscountDegree(), is(v2.getDiscountDegree()));
        assertThat(v1.getVoucherPolicy().getClass().getSimpleName(), is(v2.getVoucherPolicy().getClass().getSimpleName()));
    }
}
