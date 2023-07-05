package com.dev.voucherproject.voucher;

import com.dev.voucherproject.model.storage.voucher.VoucherDao;
import com.dev.voucherproject.model.voucher.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.dev.voucherproject.model.voucher.VoucherPolicy.*;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherDaoTest {

    @Autowired
    VoucherDao voucherDao;
    Voucher fixedAmountVoucher;
    Voucher percentDiscountVoucher;

    @BeforeAll
    void init() {
        voucherDao.deleteAll();
        fixedAmountVoucher = Voucher.of(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 5000);
        percentDiscountVoucher = Voucher.of(UUID.randomUUID(), PERCENT_DISCOUNT_VOUCHER, 55);
    }

    @Test
    @Order(1)
    @DisplayName("고정할인정책바우처를 저장할 수 있다.")
    void insertFixedAmountVoucher() {
        //WHEN
        voucherDao.insert(fixedAmountVoucher);

        //THEN
        Voucher voucher = voucherDao.findById(fixedAmountVoucher.getVoucherId()).get();
        assertThat(voucher.getVoucherId()).isEqualTo(fixedAmountVoucher.getVoucherId());
    }

    @Test
    @Order(2)
    @DisplayName("비율할인정책바우처를 저장할 수 있다.")
    void insertPercentDiscountVoucher() {
        //WHEN
        voucherDao.insert(percentDiscountVoucher);

        //THEN
        Voucher voucher = voucherDao.findById(fixedAmountVoucher.getVoucherId()).get();
        assertThat(voucher.getVoucherId()).isEqualTo(fixedAmountVoucher.getVoucherId());
    }


    @Test
    @Order(3)
    @DisplayName("전체 바우처를 조회할 수 있다.")
    void findAll() {
        //WHEN
        List<Voucher> vouchers = voucherDao.findAll();

        //THEN
        assertThat(vouchers).hasSize(2);
    }

    @Test
    @Order(4)
    @DisplayName("바우처 정책으로 조회할 수 있다")
    void findAllByPolicy() {
        //WHEN
        List<Voucher> fixedPolicyVouchers = voucherDao.findAllByPolicy(FIXED_AMOUNT_VOUCHER);
        List<Voucher> percentPolicyVouchers = voucherDao.findAllByPolicy(PERCENT_DISCOUNT_VOUCHER);

        //THEN
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(fixedPolicyVouchers).hasSize(1);
        soft.assertThat(percentPolicyVouchers).hasSize(1);
        soft.assertAll();
    }

    @Test
    @Order(5)
    @DisplayName("특정 바우처의 정책과 할인수치를 변경할 수 있다")
    void update() {
        //GIVEN
        Voucher voucher = Voucher.of(fixedAmountVoucher.getVoucherId(), PERCENT_DISCOUNT_VOUCHER, 20);

        //WHEN
        voucherDao.update(voucher);

        //THEN
        Voucher updatedVoucher = voucherDao.findById(fixedAmountVoucher.getVoucherId()).get();
        assertThat(updatedVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
        assertThat(updatedVoucher.getVoucherPolicy()).isEqualTo(voucher.getVoucherPolicy());
        assertThat(updatedVoucher.getDiscountFigure()).isEqualTo(voucher.getDiscountFigure());
    }

    @Test
    @Order(6)
    @DisplayName("특정 ID의 바우처를 조회할 수 있다")
    void findById() {
        //GIVEN
        UUID fixedAmountVoucherVoucherId = fixedAmountVoucher.getVoucherId();

        //WHEN
        Voucher findVoucher = voucherDao.findById(fixedAmountVoucherVoucherId).get();

        //THEN
        assertThat(findVoucher.getVoucherId()).isEqualTo(fixedAmountVoucher.getVoucherId());
    }

    @Test
    @Order(7)
    @DisplayName("특정 ID의 바우처를 제거할 수 있다.")
    void deleteById() {
        //GIVEN
        UUID fixedAmountVoucherVoucherId = fixedAmountVoucher.getVoucherId();

        //WHEN
        voucherDao.deleteById(fixedAmountVoucherVoucherId);

        //THEN
        List<Voucher> vouchers = voucherDao.findAll();
        assertThat(vouchers).hasSize(1);
    }

    @Test
    @Order(8)
    @DisplayName("존재하지 않는 ID 로 조회하면 Optional.empty()를 반환한다.")
    void findByNotExistId() {
        //GIVEN
        UUID notExistId = UUID.randomUUID();

        //WHEN
        Optional<Voucher> emptyVoucher = voucherDao.findById(notExistId);

        //THEN
        assertThat(emptyVoucher.isEmpty()).isTrue();
    }
}
