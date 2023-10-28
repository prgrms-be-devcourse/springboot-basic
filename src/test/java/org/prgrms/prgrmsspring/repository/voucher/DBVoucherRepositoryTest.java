package org.prgrms.prgrmsspring.repository.voucher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.prgrmsspring.entity.voucher.FixedAmountVoucher;
import org.prgrms.prgrmsspring.entity.voucher.PercentDiscountVoucher;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.exception.DataAccessException;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class DBVoucherRepositoryTest {

    @Autowired
    private VoucherRepository repository;

    @AfterEach
    void tearDown() {
        repository.clear();
    }

    @DisplayName("고정 금액 할인 바우처의 할인 양은 음수일 수 없다.")
    @Test
    void FixedAmountVoucherAmountIsNotNegative() {
        //given
        long negativeAmount = -3000;
        long zeroAmount = 0;
        long positiveAmount = 13;

        //when //then
        assertThatThrownBy(() -> new FixedAmountVoucher(UUID.randomUUID(), negativeAmount))
                .hasMessage("고정 할인 금액은 음수가 될 수 없습니다.")
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(new FixedAmountVoucher(UUID.randomUUID(), zeroAmount)).isNotNull();
        assertThat(new FixedAmountVoucher(UUID.randomUUID(), positiveAmount)).isNotNull();
    }

    @DisplayName("고정 금액 할인 바우처를 추가할 수 있다.")
    @Test
    void insertFixedAmountVoucher() {
        //given
        Voucher targetVoucher = new FixedAmountVoucher(UUID.randomUUID(), 5000);

        //when
        Voucher insertedVoucher = repository.insert(targetVoucher);

        //then
        assertThat(targetVoucher).isEqualTo(insertedVoucher);
        assertThat(targetVoucher).isInstanceOf(FixedAmountVoucher.class);
    }

    @DisplayName("비율 할인 바우처의 할인 양은 0이상 100 이하이다.")
    @Test
    void PercentDiscountVoucherRangeZeroToHundred() {
        //given
        long negativeAmount = -3000;
        long firstLimitAmount = 0;
        long inRangeAmount = 13;
        long secondLimitAmount = 100;
        long outRangeAmount = 101;

        //when //then
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), negativeAmount))
                .hasMessage("비율 할인 금액은 0과 100 사이의 금액이어야 합니다.")
                .isInstanceOf(IllegalArgumentException.class);
        assertThat(new PercentDiscountVoucher(UUID.randomUUID(), firstLimitAmount))
                .isNotNull()
                .isInstanceOf(PercentDiscountVoucher.class);
        assertThat(new PercentDiscountVoucher(UUID.randomUUID(), inRangeAmount))
                .isNotNull()
                .isInstanceOf(PercentDiscountVoucher.class);
        assertThat(new PercentDiscountVoucher(UUID.randomUUID(), secondLimitAmount))
                .isNotNull()
                .isInstanceOf(PercentDiscountVoucher.class);
        assertThatThrownBy(() -> new PercentDiscountVoucher(UUID.randomUUID(), outRangeAmount))
                .hasMessage("비율 할인 금액은 0과 100 사이의 금액이어야 합니다.")
                .isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("비율 할인 바우처를 추가할 수 있다.")
    @Test
    void insertPercentDiscountVoucher() {
        //given
        Voucher targetVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 30);

        //when
        Voucher insertedVoucher = repository.insert(targetVoucher);

        //then
        assertThat(targetVoucher).isEqualTo(insertedVoucher);
        assertThat(targetVoucher).isInstanceOf(PercentDiscountVoucher.class);
    }

    @DisplayName("저장소 내의 모든 바우처를 조회할 수 있다.")
    @Test
    void findAll() {
        //given
        FixedAmountVoucher fixedAmountVoucher1 = new FixedAmountVoucher(UUID.randomUUID(), 3000);
        FixedAmountVoucher fixedAmountVoucher2 = new FixedAmountVoucher(UUID.randomUUID(), 200);
        PercentDiscountVoucher percentDiscountVoucher1 = new PercentDiscountVoucher(UUID.randomUUID(), 30);
        PercentDiscountVoucher percentDiscountVoucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 50);

        List<Voucher> vouchers = List.of(fixedAmountVoucher1, fixedAmountVoucher2, percentDiscountVoucher1, percentDiscountVoucher2);
        vouchers.forEach(repository::insert);

        //when
        List<Voucher> targetVouchers = repository.findAll();

        //then
        assertThat(targetVouchers).hasSize(4)
                .containsOnlyOnceElementsOf(vouchers);
    }

    @DisplayName("바우처 아이디를 통해 바우처를 조회할 수 있다.")
    @Test
    void findVoucherById() {
        //given
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 300);
        PercentDiscountVoucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50);

        repository.insert(fixedAmountVoucher);
        repository.insert(percentDiscountVoucher);

        UUID fixedAmountVoucherVoucherId = fixedAmountVoucher.getVoucherId();
        UUID percentDiscountVoucherVoucherId = percentDiscountVoucher.getVoucherId();

        //when
        Optional<Voucher> optionalFixedAmountVoucher = repository.findById(fixedAmountVoucherVoucherId);
        Optional<Voucher> optionalPercentDiscountVoucher = repository.findById(percentDiscountVoucherVoucherId);

        //then
        assertThat(optionalFixedAmountVoucher).contains(fixedAmountVoucher);
        assertThat(optionalPercentDiscountVoucher).contains(percentDiscountVoucher);
    }

    @DisplayName("바우처를 업데이트할 수 있다.")
    @Test
    void updateVoucher() {
        //given
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300);
        UUID voucherId = voucher.getVoucherId();
        repository.insert(voucher);

        PercentDiscountVoucher targetVoucher = new PercentDiscountVoucher(voucherId, 20);

        //when
        Voucher updatedVoucher = repository.update(targetVoucher);

        //then
        assertThat(updatedVoucher).isEqualTo(targetVoucher);
    }

    @DisplayName("바우처 업데이트를 하려면 업데이트하려는 바우처의 아이디가 기존의 바우처의 아이디와 동일해야 한다.")
    @Test
    void updateVoucher2() {
        //given
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300);
        UUID voucherId = voucher.getVoucherId();
        repository.insert(voucher);

        PercentDiscountVoucher targetVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 20);

        //when //then
        assertThatThrownBy(() -> repository.update(targetVoucher))
                .isInstanceOf(DataAccessException.class)
                .hasMessageContaining(ExceptionMessage.UPDATE_QUERY_FAILED.getMessage());
    }

    @DisplayName("바우처 아이디를 활용해 바우처를 삭제할 수 있다.")
    @Test
    void deleteVoucher() {
        //given
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 300);
        UUID voucherId = voucher.getVoucherId();
        repository.insert(voucher);

        //when
        repository.delete(voucherId);

        //then
        assertThat(repository.findById(voucherId)).isEmpty();
    }

    @DisplayName("존재하지 않은 바우처 아이디를 통해서는 바우처를 삭제할 수 없다.")
    @Test
    void deleteVoucher2() {
        //given
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 2000);
        UUID voucherId = voucher.getVoucherId();

        //when //then
        assertThatThrownBy(() -> repository.delete(voucherId))
                .isInstanceOf(DataAccessException.class)
                .hasMessageContaining(ExceptionMessage.DELETE_QUERY_FAILED.getMessage());
    }
}