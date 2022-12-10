package org.prgrms.java.repository.voucher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.java.domain.voucher.FixedAmountVoucher;
import org.prgrms.java.domain.voucher.PercentDiscountVoucher;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.badrequest.VoucherBadRequestException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import static org.junit.jupiter.api.Assertions.*;

class MemoryVoucherRepositoryTest {
    private static final VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @BeforeEach
    @AfterEach
    void clean() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처를 등록할 수 있다.")
    void testInsert() {
        Voucher fixedAmountVoucher = createFixedAmountVoucher(UUID.randomUUID());
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(UUID.randomUUID());

        Voucher insertedFixedAmountVoucher = voucherRepository.insert(fixedAmountVoucher);
        Voucher insertedPercentDiscountVoucher = voucherRepository.insert(percentDiscountVoucher);

        assertThat(fixedAmountVoucher, samePropertyValuesAs(insertedFixedAmountVoucher));
        assertThat(percentDiscountVoucher, samePropertyValuesAs(insertedPercentDiscountVoucher));
    }

    @Test
    @DisplayName("동일한 ID의 바우처는 등록할 수 없다.")
    void testInsertSameIdVoucher() {
        assertThrows(VoucherBadRequestException.class, () -> {
            UUID voucherId = UUID.randomUUID();
            Voucher fixedAmountVoucher = createFixedAmountVoucher(voucherId);
            Voucher percentDiscountVoucher = createPercentDiscountVoucher(voucherId);

            voucherRepository.insert(fixedAmountVoucher);
            voucherRepository.insert(percentDiscountVoucher);
        });
    }

    @Test
    @DisplayName("등록한 바우처를 ID, 소유자 ID로 찾을 수 있다.")
    void testFindById() {
        Voucher fixedAmountVoucher = createFixedAmountVoucher(UUID.randomUUID());
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(UUID.randomUUID());
        UUID ownerId = UUID.randomUUID();
        percentDiscountVoucher.setOwnerId(ownerId);

        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        assertThat(voucherRepository.findById(fixedAmountVoucher.getVoucherId()).orElseThrow(), samePropertyValuesAs(fixedAmountVoucher));
        assertThat(voucherRepository.findByCustomer(ownerId), hasSize(1));
        assertThat(voucherRepository.findByCustomer(ownerId).get(0), samePropertyValuesAs(percentDiscountVoucher));
        assertThat(voucherRepository.findById(fixedAmountVoucher.getVoucherId()).orElseThrow(), not(samePropertyValuesAs((percentDiscountVoucher))));
    }

    @Test
    @DisplayName("등록한 바우처와 전체 인스턴스의 개수가 일치한다.")
    void testFindAll() {
        Voucher fixedAmountVoucher = createFixedAmountVoucher(UUID.randomUUID());
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(UUID.randomUUID());

        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        assertThat(voucherRepository.findAll().isEmpty(), is(false));
        assertThat(voucherRepository.findAll(), hasSize(2));
    }

    @Test
    @DisplayName("전체 바우처를 삭제할 수 있다.")
    void testDeleteAll() {
        Voucher fixedAmountVoucher = createFixedAmountVoucher(UUID.randomUUID());
        Voucher percentDiscountVoucher = createPercentDiscountVoucher(UUID.randomUUID());

        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);
        voucherRepository.deleteAll();

        assertThat(voucherRepository.findAll().isEmpty(), is(true));
    }

    private Voucher createFixedAmountVoucher(UUID voucherId) {
        return FixedAmountVoucher.builder()
                .voucherId((voucherId != null) ? voucherId : UUID.randomUUID())
                .amount(1000)
                .isUsed(false)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now())
                .build();
    }

    private Voucher createPercentDiscountVoucher(UUID voucherId) {
        return PercentDiscountVoucher.builder()
                .voucherId((voucherId != null) ? voucherId : UUID.randomUUID())
                .amount(50)
                .isUsed(false)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now())
                .build();
    }
}