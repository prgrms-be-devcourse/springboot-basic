package org.prgrms.prgrmsspring.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.prgrmsspring.entity.voucher.FixedAmountVoucher;
import org.prgrms.prgrmsspring.entity.voucher.PercentDiscountVoucher;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.repository.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class VoucherServiceTest {

    @Autowired
    private VoucherService service;

    @Autowired
    private VoucherRepository repository;

    @AfterEach
    void tearDown() {
        repository.clear();
    }


    @DisplayName("바우처를 추가할 수 있다.")
    @Test
    void createVoucher() {
        //given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 50);

        //when
        Voucher createFixedAmountVoucher = service.create(fixedAmountVoucher);
        Voucher createPercentDiscountVoucher = service.create(percentDiscountVoucher);

        //then
        assertThat(createFixedAmountVoucher).isEqualTo(fixedAmountVoucher);
        assertThat(createPercentDiscountVoucher).isEqualTo(percentDiscountVoucher);
    }

    @DisplayName("기존의 바우처를 수정할 수 있다.")
    @Test
    void updateExistingVoucher() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        UUID existingVoucherId = voucher.getVoucherId();
        service.create(voucher);

        Voucher targetVoucher = new PercentDiscountVoucher(existingVoucherId, 50);
        //when
        Voucher updatedVoucher = service.update(targetVoucher);

        //then
        assertThat(updatedVoucher).isEqualTo(targetVoucher);
        assertThat(updatedVoucher.getVoucherId()).isEqualTo(existingVoucherId);
        assertThat(updatedVoucher.getAmount()).isNotEqualTo(voucher.getAmount());
    }

    @DisplayName("기존 바우처가 없는 경우 수정할 수 없다.")
    @Test
    void updateNotExistingVoucher() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        UUID existingVoucherId = voucher.getVoucherId();
        Voucher targetVoucher = new PercentDiscountVoucher(existingVoucherId, 50);

        //when //then
        assertThatThrownBy(() -> service.update(targetVoucher))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.NOT_FOUND_VOUCHER.getMessage());
    }

    @DisplayName("기존 바우처를 삭제할 수 있다.")
    @Test
    void deleteExistingVoucher() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        UUID existingVoucherId = voucher.getVoucherId();
        service.create(voucher);

        //when
        service.delete(existingVoucherId);

        //then
        assertThat(service.findAll()).isEmpty();
    }

    @DisplayName("기존 바우처가 존재하지 않는 경우 삭제할 수 없다.")
    @Test
    void deleteNotExistingVoucher() {
        //given
        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        UUID existingVoucherId = voucher.getVoucherId();

        //when //then
        assertThatThrownBy(() -> service.delete(existingVoucherId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ExceptionMessage.NOT_FOUND_VOUCHER.getMessage());
    }

    @DisplayName("저장되어 있는 바우처 전체를 조회할 수 있다.")
    @Test
    void findAll() {
        //given
        Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), 50);
        Voucher voucher3 = new FixedAmountVoucher(UUID.randomUUID(), 100);
        List<Voucher> vouchers = List.of(voucher1, voucher2, voucher3);
        vouchers.forEach(service::create);

        //when
        List<Voucher> allVouchers = service.findAll();

        //then
        assertThat(allVouchers).hasSize(vouchers.size())
                .containsOnlyOnceElementsOf(vouchers);
    }
}