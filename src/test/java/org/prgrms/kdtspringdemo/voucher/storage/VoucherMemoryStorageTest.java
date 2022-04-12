package org.prgrms.kdtspringdemo.voucher.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.PercentDiscountVoucher;
import org.prgrms.kdtspringdemo.voucher.voucherdetail.Voucher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class VoucherMemoryStorageTest {

    @Test
    @DisplayName("memory storage 에 아이템 저장테스트")
    public void storageTest () throws Exception{
        // given
        VoucherStorage voucherMemoryStorage = new VoucherMemoryStorage();

        UUID fixedVoucherUUID = UUID.randomUUID();
        Voucher fixedVoucher = new FixedAmountVoucher(fixedVoucherUUID, 50);
        UUID percentVoucherUUID = UUID.randomUUID();
        Voucher percentVoucher = new PercentDiscountVoucher(percentVoucherUUID, 30);

        // when
        voucherMemoryStorage.insert(fixedVoucher);
        voucherMemoryStorage.insert(percentVoucher);
        
        // then
        Assertions.assertEquals(fixedVoucherUUID, voucherMemoryStorage.findById(fixedVoucherUUID).get().getVoucherId());
        Assertions.assertEquals(50, voucherMemoryStorage.findById(fixedVoucherUUID).get().getAmount());
        Assertions.assertEquals(percentVoucherUUID, voucherMemoryStorage.findById(percentVoucherUUID).get().getVoucherId());
        Assertions.assertEquals(30, voucherMemoryStorage.findById(percentVoucherUUID).get().getAmount());
        
    }

}