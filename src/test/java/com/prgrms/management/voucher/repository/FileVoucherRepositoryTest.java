package com.prgrms.management.voucher.repository;

import com.prgrms.management.voucher.domain.FixedAmountVoucher;
import com.prgrms.management.voucher.domain.PercentAmountVoucher;
import com.prgrms.management.voucher.domain.Voucher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class FileVoucherRepositoryTest {

    FileVoucherRepository voucherRepository = new FileVoucherRepository();

    FileVoucherRepositoryTest() throws IOException {
    }

    @Test
    void Voucher_File에_저장() {
        //given
        FixedAmountVoucher voucher = new FixedAmountVoucher(100);
        PercentAmountVoucher voucherTwo = new PercentAmountVoucher(5);
        //when
        Voucher insert = voucherRepository.insert(voucher);
        Voucher insertTwo = voucherRepository.insert(voucherTwo);
        //then
        Assertions.assertThat(insert).isEqualTo(voucher);
        Assertions.assertThat(insertTwo).isEqualTo(voucherTwo);
    }

}