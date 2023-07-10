package me.kimihiqq.vouchermanagement.domain.voucher.repository;

import me.kimihiqq.vouchermanagement.domain.voucher.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("바우처 정보를 저장할 수 있다")
    void saveVoucher() {
        // given
        voucherRepository.save(fixedVoucher);
        // when
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(fixedVoucher.getVoucherId());
        // then
        assertThat(retrievedVoucher.isPresent(), is(true));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(fixedVoucher));
    }

    @Test
    @DisplayName("상품권 ID로 바우처을 찾을 수 있다")
    void findVoucherById() {
        // given
        voucherRepository.save(fixedVoucher);
        // when
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(fixedVoucher.getVoucherId());
        // then
        assertThat(retrievedVoucher.isPresent(), is(true));
        assertThat(retrievedVoucher.get(), samePropertyValuesAs(fixedVoucher));
    }

    @Test
    @DisplayName("모든 바우처을 찾을 수 있다")
    void findAllVouchers() {
        // given
        voucherRepository.save(fixedVoucher);
        voucherRepository.save(percentVoucher);
        // when
        List<Voucher> vouchers = voucherRepository.findAll();
        // then
        assertThat(vouchers, containsInAnyOrder(
                Matchers.samePropertyValuesAs(fixedVoucher),
                Matchers.samePropertyValuesAs(percentVoucher)
        ));
    }

    @Test
    @DisplayName("존재하지 않는 바우처을 찾으려고 시도하면 결과가 없다")
    void voucherNotFound() {
        // when
        Optional<Voucher> retrievedVoucher = voucherRepository.findById(UUID.randomUUID());
        // then
        assertThat(retrievedVoucher.isPresent(), is(false));
    }
}