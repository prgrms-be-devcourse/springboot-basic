package org.programmers.kdtspring.repository.voucher;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.kdtspring.entity.voucher.FixedAmountVoucher;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.entity.voucher.VoucherType;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class FileVoucherRepositoryTest {

    FileVoucherRepository fileVoucherRepository = new FileVoucherRepository();
    Voucher voucher;

    FileVoucherRepositoryTest() throws IOException {
    }

    @BeforeEach
    void setup() {
        voucher = new FixedAmountVoucher(UUID.randomUUID(), 10000, VoucherType.FixedAmountVoucher.name());
    }

    @Test
    @DisplayName("바우처 저장")
    void testInsert() {
        fileVoucherRepository.insert(voucher);

        assertThat(fileVoucherRepository.findAll())
                .extracting(Voucher::getVoucherId)
                .contains(voucher.getVoucherId());
    }
}