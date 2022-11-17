package org.prgrms.kdt.storage;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.io.FileIO;
import org.prgrms.kdt.utils.FileParser;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FileVoucherStorageTest {

    private final FileIO fileIO = new FileIO();
    private final FileParser fileParser = new FileParser(fileIO);
    private final VoucherStorage voucherStorage = new FileVoucherStorage(fileParser);

    @Test
    @DisplayName("파일에 바우처를 저장할 수 있다.")
    void testSave() {
        // given
        Voucher fixedAmountVoucher = createFixedAmountVoucher();
        Voucher percentAmountVoucher = createPercentDiscountVoucher();
        // when
        voucherStorage.save(fixedAmountVoucher);
        voucherStorage.save(percentAmountVoucher);
        // then
        assertThat(voucherStorage.findById(
                        fixedAmountVoucher.getVoucherId())
                .get())
                .usingRecursiveComparison()
                .isEqualTo(fixedAmountVoucher);

        assertThat(voucherStorage.findById(
                        fixedAmountVoucher.getVoucherId())
                .get())
                .usingRecursiveComparison()
                .isEqualTo(fixedAmountVoucher);
    }

    @Test
    @DisplayName("파일에 저장된 바우처 정보를 모두 불러올 수 있다.")
    void testFindAllVoucher() {
        List<Voucher> vouchers = voucherStorage.findAll();
        List<String> readVouchers = fileParser.getVoucherIdList();
        assertEquals(readVouchers.size(), vouchers.size());
    }

    private Voucher createFixedAmountVoucher() {
        return new FixedAmountVoucher(UUID.randomUUID(), 1000);
    }

    private Voucher createPercentDiscountVoucher() {
        return new PercentDiscountVoucher(UUID.randomUUID(), 50);
    }
}