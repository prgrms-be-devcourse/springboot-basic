package com.mountain.voucherApp.voucher.repository;

import com.mountain.voucherApp.voucher.PercentDiscountVoucher;
import com.mountain.voucherApp.voucher.Voucher;
import com.mountain.voucherApp.voucher.VoucherEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

class MemoryVoucherRepositoryTest {

    MemoryVoucherRepository voucherRepository;

    @BeforeEach
    void init() {
        voucherRepository = new MemoryVoucherRepository();
        voucherRepository.clear();
    }

    @DisplayName("저장 테스트")
    @Test
    public void insertTest() throws Exception {
        //given
        Voucher voucher = new PercentDiscountVoucher();

        VoucherEntity voucherEntity = new VoucherEntity(UUID.randomUUID(), 1, 1000L);

        //when
        VoucherEntity saved = voucherRepository.insert(voucherEntity);
        //then
        Optional<VoucherEntity> findVoucher = voucherRepository.findById(saved.getVoucherId());
        Assertions.assertEquals(saved.getVoucherId(), findVoucher.get().getVoucherId());
    }

    @DisplayName("findAll 테스트")
    @Test
    public void findAllTest() throws Exception {
        //given
        for (int i = 1; i <= 10; i++) {
            VoucherEntity entity = new VoucherEntity(UUID.randomUUID(),
                    1,
                    100L);
            voucherRepository.insert(entity);
        }
        //when
        List<VoucherEntity> all = voucherRepository.findAll();
        System.out.println(all);
        //then
        Assertions.assertEquals(10, all.size());
    }

    @DisplayName("할인 정책과 금액을 조건으로 데이터를 검색할 수 있다.")
    @Test
    public void findDiscountPolicyAndAmountTest() throws Exception {
        // given
        VoucherEntity voucherEntity = new VoucherEntity(UUID.randomUUID(), 2, 3L);
        // when
        voucherRepository.insert(voucherEntity);
        Optional<VoucherEntity> existVoucher = voucherRepository.findByPolicyIdAndDiscountAmount(voucherEntity.getDiscountPolicyId(), voucherEntity.getDiscountAmount());
        Optional<VoucherEntity> notExistVoucher = voucherRepository.findByPolicyIdAndDiscountAmount(voucherEntity.getDiscountPolicyId(), voucherEntity.getDiscountAmount() * 2);
        // then
        assertThat(existVoucher.isEmpty(), is(false));
        assertThat(notExistVoucher.isEmpty(), is(true));
        assertThat(existVoucher.get(), samePropertyValuesAs(voucherEntity));
    }

}