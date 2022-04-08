package org.prgrms.vouchermanager.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemoryVoucherRepositoryTest {

    private final VoucherRepository voucherRepository = new MemoryVoucherRepository();

    @Test
    @DisplayName("저장 테스트")
    void saveTest(){
        //given
        Voucher voucher = new FixedAmountVoucher(10);

        //when
        Voucher insertVoucher = voucherRepository.insert(voucher);

        //then
    }

}