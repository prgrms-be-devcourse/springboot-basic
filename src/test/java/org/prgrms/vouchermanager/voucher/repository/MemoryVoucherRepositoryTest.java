package org.prgrms.vouchermanager.voucher.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.vouchermanager.voucher.domain.FixedAmountVoucher;
import org.prgrms.vouchermanager.voucher.domain.Voucher;

import java.util.Optional;

class MemoryVoucherRepositoryTest {

    private final VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @Test
    @DisplayName("저장 및 조회 테스트")
    void saveTest(){
        //given
        Voucher voucher = new FixedAmountVoucher(10);
        voucherRepository.insert(voucher);

        //when
        Optional<Voucher> findVoucher = voucherRepository.findById(voucher.getVoucherId());

        //then
        Assertions.assertThat(voucher).isEqualTo(findVoucher.get());

    }

}