package me.kimihiqq.vouchermanagement.domain.voucher.repository;

import me.kimihiqq.vouchermanagement.domain.voucher.FixedAmountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.PercentDiscountVoucher;
import me.kimihiqq.vouchermanagement.domain.voucher.Voucher;
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
        assertThat(retrievedVoucher.get().getDiscount(), is(fixedVoucher.getDiscount()));
        assertThat(retrievedVoucher.get().getType(), is(fixedVoucher.getType()));
        assertThat(retrievedVoucher.get().getVoucherId(), is(fixedVoucher.getVoucherId()));
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
        assertThat(retrievedVoucher.get().getDiscount(), is(fixedVoucher.getDiscount()));
        assertThat(retrievedVoucher.get().getType(), is(fixedVoucher.getType()));
        assertThat(retrievedVoucher.get().getVoucherId(), is(fixedVoucher.getVoucherId()));
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
        assertThat(vouchers, hasItems(hasProperty("discount", is(fixedVoucher.getDiscount())),
                hasProperty("type", is(fixedVoucher.getType())),
                hasProperty("voucherId", is(fixedVoucher.getVoucherId()))));
        assertThat(vouchers, hasItems(hasProperty("discount", is(percentVoucher.getDiscount())),
                hasProperty("type", is(percentVoucher.getType())),
                hasProperty("voucherId", is(percentVoucher.getVoucherId()))));
    }

}
