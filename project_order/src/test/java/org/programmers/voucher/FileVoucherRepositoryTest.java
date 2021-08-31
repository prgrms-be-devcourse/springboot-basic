package org.programmers.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

class FileVoucherRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(FileVoucherRepositoryTest.class);

    private final VoucherRepository fileVoucherRepository;

    FileVoucherRepositoryTest() throws IOException {
        fileVoucherRepository = new FileVoucherRepository();
    }

    @Test
    @DisplayName("특정 id를 가진 바우처를 불러온다")
    void findById() {
        // given
        UUID voucherId = UUID.randomUUID();
        Voucher fixedAmountVoucher = new FixedAmountVoucher(voucherId, 10L);

        fileVoucherRepository.insert(fixedAmountVoucher);

        // when
        Optional<Voucher> voucher = fileVoucherRepository.findById(voucherId);

        // then
        assertThat(voucher.isEmpty(), is(false));
        assertThat(voucher.get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
        assertThat(voucher.get().getValue(), is(fixedAmountVoucher.getValue()));
    }

    @Test
    @DisplayName("새로운 바우처가 저장된다")
    void insert() {
        // given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);

        // when
        Voucher insertedVoucher = fileVoucherRepository.insert(fixedAmountVoucher);

        // then
        assertThat(insertedVoucher, is(fixedAmountVoucher));
    }

    @Test
    @DisplayName("현재 파일에 저장된 바우처를 모두 불러온다")
    void getAllVouchers() {
        // given
        List<Voucher> vouchers = fileVoucherRepository.getAllVouchers();

        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        Voucher percentDiscountVoucher = new PercentDiscountVoucher(UUID.randomUUID(), 10L);

        fileVoucherRepository.insert(fixedAmountVoucher);
        fileVoucherRepository.insert(percentDiscountVoucher);

        // when
        List<Voucher> vouchers2 = fileVoucherRepository.getAllVouchers();

        // then
        assertThat(vouchers2.isEmpty(), is(false));
        assertThat(vouchers2.size(), is(vouchers.size() + 2));
    }
}