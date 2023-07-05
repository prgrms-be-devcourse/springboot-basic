package me.kimihiqq.vouchermanagement.domain.voucher.repository;

import me.kimihiqq.vouchermanagement.domain.voucher.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class VoucherRepositoryTest {

    @Autowired
    private VoucherRepository voucherRepository;

    private Voucher fixedVoucher;
    private Voucher percentVoucher;

    @BeforeEach
    void setUp() {
        fixedVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        percentVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10);
    }

    @AfterEach
    void tearDown() {
        voucherRepository.findAll().forEach(voucher ->
                voucherRepository.deleteById(voucher.getVoucherId())
        );
    }

    @Test
    void saveVoucher() {
        voucherRepository.save(fixedVoucher);
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(fixedVoucher.getVoucherId());

        assertThat(retrievedVoucher.isPresent(), is(true));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(fixedVoucher));
    }

    @Test
    void findVoucherById() {
        voucherRepository.save(fixedVoucher);
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(fixedVoucher.getVoucherId());

        assertThat(retrievedVoucher.isPresent(), is(true));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(fixedVoucher));
    }

    @Test
    void findAllVouchers() {
        voucherRepository.save(fixedVoucher);
        voucherRepository.save(percentVoucher);

        List<Voucher> vouchers = voucherRepository.findAll();
        assertThat(vouchers, containsInAnyOrder(
                Matchers.samePropertyValuesAs(fixedVoucher),
                Matchers.samePropertyValuesAs(percentVoucher)
        ));
    }

    @Test
    void voucherNotFound() {
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(UUID.randomUUID());

        assertThat(retrievedVoucher.isPresent(), is(false));
    }
}