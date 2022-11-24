package org.prgrms.java.repository.voucher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.java.domain.voucher.FixedAmountVoucher;
import org.prgrms.java.domain.voucher.PercentDiscountVoucher;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.VoucherException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileVoucherRepositoryTest {
    private static final VoucherRepository voucherRepository = new FileVoucherRepository("data", "voucher_test.csv");

    @BeforeEach
    @AfterEach
    void clean() {
        voucherRepository.deleteAll();
    }

    @Test
    @DisplayName("바우처를 파일로 등록할 수 있다.")
    void testInsert() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now(), LocalDateTime.now());

        Voucher insertedFixedAmountVoucher = voucherRepository.insert(fixedAmountVoucher);

        assertThat(fixedAmountVoucher, samePropertyValuesAs(insertedFixedAmountVoucher));
    }

    @Test
    @DisplayName("동일한 ID의 바우처는 파일에 등록할 수 없다.")
    void testInsertSameIdVoucher() {
        assertThrows(VoucherException.class, () -> {
            UUID voucherId = UUID.randomUUID();
            Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 100, LocalDateTime.now(), LocalDateTime.now());
            Voucher percentDiscountVoucher = new PercentDiscountVoucher(voucherId, 10, LocalDateTime.now(), LocalDateTime.now());

            voucherRepository.insert(fixedAmountVoucher);
            voucherRepository.insert(percentDiscountVoucher);
        });
    }

    @Test
    @DisplayName("등록한 바우처를 파일에서 찾을 수 있다.")
    void testFindById() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now(), LocalDateTime.now());
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10, LocalDateTime.now(), LocalDateTime.now());

        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        assertThat(voucherRepository.findById(fixedAmountVoucher.getVoucherId()).get(), samePropertyValuesAs(fixedAmountVoucher));
        assertThat(voucherRepository.findById(percentDiscountVoucher.getVoucherId()).get(), samePropertyValuesAs(percentDiscountVoucher));
        assertThat(voucherRepository.findById(fixedAmountVoucher.getVoucherId()).get(), not(samePropertyValuesAs((percentDiscountVoucher))));
    }

    @Test
    @DisplayName("파일에 등록한 바우처와 전체 인스턴스의 개수가 일치한다.")
    void testFindAll() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now(), LocalDateTime.now());
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 100, LocalDateTime.now(), LocalDateTime.now());

        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        assertThat(voucherRepository.findAll().isEmpty(), is(false));
        assertThat(voucherRepository.findAll(), hasSize(2));
    }

    @Test
    @DisplayName("파일에 등록한 개수와 전체 삭제한 개수가 같다.")
    void testDeleteAll() {
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 100, LocalDateTime.now(), LocalDateTime.now());
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 100, LocalDateTime.now(), LocalDateTime.now());

        voucherRepository.insert(fixedAmountVoucher);
        voucherRepository.insert(percentDiscountVoucher);

        assertThat(voucherRepository.deleteAll(), is(2L));
    }
}