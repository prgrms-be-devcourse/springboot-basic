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
class VoucherDaoTest {

    @Autowired
    VoucherDao voucherDao;

    void insertFixedAmountPolicyVouchers() {
        Voucher fixedAmountVoucherA = Voucher.of(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 5000);
        Voucher fixedAmountVoucherB = Voucher.of(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 6000);
        Voucher fixedAmountVoucherC = Voucher.of(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 6000);
        voucherDao.insert(fixedAmountVoucherA);
        voucherDao.insert(fixedAmountVoucherB);
        voucherDao.insert(fixedAmountVoucherC);
    }

    void insertPercentDiscountPolicyVouchers() {
        Voucher percentDiscountVoucherA = Voucher.of(UUID.randomUUID(), PERCENT_DISCOUNT_VOUCHER, 40);
        Voucher percentDiscountVoucherB = Voucher.of(UUID.randomUUID(), PERCENT_DISCOUNT_VOUCHER, 50);
        voucherDao.insert(percentDiscountVoucherA);
        voucherDao.insert(percentDiscountVoucherB);
    }
    @BeforeEach
    void init() {
        voucherDao.deleteAll();
    }

    @Test
    @DisplayName("고정할인정책바우처를 저장할 수 있다.")
    void insertFixedAmountVoucher() {
        //GIVEN
        Voucher fixedAmountVoucher = Voucher.of(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 5000);

        //WHEN
        voucherDao.insert(fixedAmountVoucher);

        //THEN
        Voucher voucher = voucherDao.findById(fixedAmountVoucher.getVoucherId()).get();
        assertThat(voucher.getVoucherId()).isEqualTo(fixedAmountVoucher.getVoucherId());
    }

    @Test
    @DisplayName("비율할인정책바우처를 저장할 수 있다.")
    void insertPercentDiscountVoucher() {
        //GIVEN
        Voucher percentDiscountVoucher = Voucher.of(UUID.randomUUID(), PERCENT_DISCOUNT_VOUCHER, 60);

        //WHEN
        voucherDao.insert(percentDiscountVoucher);

        //THEN
        Voucher voucher = voucherDao.findById(percentDiscountVoucher.getVoucherId()).get();
        assertThat(voucher.getVoucherId()).isEqualTo(percentDiscountVoucher.getVoucherId());
    }


    @Test
    @DisplayName("전체 바우처를 조회할 수 있다.")
    void findAll() {
        //GIVEN
        insertFixedAmountPolicyVouchers();
        insertPercentDiscountPolicyVouchers();

        //WHEN
        List<Voucher> vouchers = voucherDao.findAll();

        //THEN
        assertThat(vouchers).hasSize(5);
    }

    @Test
    @DisplayName("고정할인정책으로 바우처를 조회할 수 있다")
    void findAllFixed_Amount_Vouchers() {
        //GIVEN
        insertFixedAmountPolicyVouchers();
        insertPercentDiscountPolicyVouchers();

        //WHEN
        List<Voucher> fixedAmountVouchers = voucherDao.findAllByPolicy(FIXED_AMOUNT_VOUCHER);

        //THEN
        assertThat(fixedAmountVouchers).hasSize(3);
    }

    @Test
    @DisplayName("비율할인정책으로 바우처를 조회할 수 있다")
    void findAllPercent_discount_vouchers() {
        //GIVEN
        insertFixedAmountPolicyVouchers();
        insertPercentDiscountPolicyVouchers();

        //WHEN
        List<Voucher> percentDiscountVouchers = voucherDao.findAllByPolicy(PERCENT_DISCOUNT_VOUCHER);

        //THEN
        assertThat(percentDiscountVouchers).hasSize(2);
    }

    @Test
    @DisplayName("특정 바우처의 정책과 할인수치를 변경할 수 있다")
    void update() {
        //GIVEN
        Voucher voucher = Voucher.of(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 5000);
        voucherDao.insert(voucher);

        //WHEN
        voucherDao.update(Voucher.of(voucher.getVoucherId(), PERCENT_DISCOUNT_VOUCHER, 30));

        //THEN
        Voucher voucherAfterUpdate = voucherDao.findById(voucher.getVoucherId()).get();

        SoftAssertions.assertSoftly(soft -> {
            soft.assertThat(voucherAfterUpdate.getVoucherId()).isEqualTo(voucher.getVoucherId());
            soft.assertThat(voucherAfterUpdate.getPolicyName()).isEqualTo(PERCENT_DISCOUNT_VOUCHER);
            soft.assertThat(voucherAfterUpdate.getDiscountFigure()).isEqualTo(30);
        });
    }

    @Test
    @DisplayName("특정 ID의 바우처를 조회할 수 있다")
    void findById() {
        //GIVEN
        Voucher voucher = Voucher.of(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 5000);
        voucherDao.insert(voucher);

        //WHEN
        Voucher findVoucher = voucherDao.findById(voucher.getVoucherId()).get();

        //THEN
        assertThat(findVoucher.getVoucherId()).isEqualTo(voucher.getVoucherId());
    }

    @Test
    @DisplayName("특정 ID의 바우처를 제거할 수 있다.")
    void deleteById() {
        //GIVEN
        Voucher voucher = Voucher.of(UUID.randomUUID(), FIXED_AMOUNT_VOUCHER, 5000);
        voucherDao.insert(voucher);

        //WHEN
        voucherDao.deleteById(voucher.getVoucherId());

        //THEN
        List<Voucher> vouchers = voucherDao.findAll();
        assertThat(vouchers).hasSize(0);
    }

    @Test
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
