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
    @DisplayName("파일에 두 가지 타입의 바우처를 모두 저장할 수 있다.")
    void testSave() {
        // given
        Voucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID().toString(), 1000);
        Voucher percentAmountVoucher = new PercentDiscountVoucher(UUID.randomUUID().toString(), 50);

        // when
        voucherStorage.save(fixedAmountVoucher);
        voucherStorage.save(percentAmountVoucher);

        // then
        voucherStorage.findById(fixedAmountVoucher.getVoucherId())
                .ifPresent(findVoucher ->
                        assertThat(findVoucher).usingRecursiveComparison()
                                .isEqualTo(fixedAmountVoucher));

        voucherStorage.findById(percentAmountVoucher.getVoucherId())
                .ifPresent(findVoucher ->
                        assertThat(findVoucher).usingRecursiveComparison()
                                .isEqualTo(percentAmountVoucher));
    }

    @Test
    @DisplayName("파일로 저장된 바우처 정보들을 모두 불러올 수 있다.")
    void testFindAllVoucher() {
        // given
        List<String> voucherIds = fileParser.getFileList();

        // when
        List<Voucher> vouchers = voucherStorage.findAll();

        // then
        assertEquals(voucherIds.size(), vouchers.size());
    }

    @Test
    @DisplayName("바우처 Id를 이용하여 바우처를 삭제할 수 있다.")
    void testDeleteById() {
        // given
        String newVoucherId = UUID.randomUUID().toString();
        voucherStorage.save(new PercentDiscountVoucher(newVoucherId, 20));

        // when
        voucherStorage.deleteById(newVoucherId);

        // then
        assertFalse(fileParser.getFileList().contains(newVoucherId));
    }
}