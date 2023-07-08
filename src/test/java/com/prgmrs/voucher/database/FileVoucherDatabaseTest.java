package com.prgmrs.voucher.database;

import com.prgmrs.voucher.exception.FileNotReadException;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.vo.Amount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("파일 바우처 저장소 테스트")
class FileVoucherDatabaseTest {
    @Test
    @DisplayName("정상적으로 바우처 파일을 로딩 한다.")
    void FileLoadWithoutExceptionTest() {
        final String FILE_PATH = "csv/vouchers.csv";
        FileVoucherDatabase database = new FileVoucherDatabase();
        Map<UUID, Voucher> result = database.load(FILE_PATH);

        assertNotNull(result);
    }

    @Test
    @DisplayName("없는 바우처 파일에 쓰기를 시도한다.")
    void NotExistingFileEmptyReturnTest() {
        final String FILE_PATH = "not/existing.csv";
        FileVoucherDatabase database = new FileVoucherDatabase();

        assertThat(database.load(FILE_PATH), is(anEmptyMap()));
    }

    @Test
    @DisplayName("정상적으로 바우처 파일에 쓴다.")
    void SuccessfulFileWritingTest() {
        final String FILE_PATH = "csv/vouchers.csv";
        UUID randomVoucherId = UUID.randomUUID();
        Amount amount = new Amount(3000);
        FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
        Voucher voucher = new Voucher(randomVoucherId, fixedAmountDiscountStrategy);

        FileVoucherDatabase database = new FileVoucherDatabase();

        assertDoesNotThrow(() -> database.store(voucher, FILE_PATH));
    }

    @Test
    @DisplayName("없는 바우처 파일에 쓰기를 시도한다.")
    void NotExistingFileWritingTest() {
        final String FILE_PATH = "not/existing.csv";
        UUID randomVoucherId = UUID.randomUUID();
        Amount amount = new Amount(3000);
        FixedAmountDiscountStrategy fixedAmountDiscountStrategy = new FixedAmountDiscountStrategy(amount);
        Voucher voucher = new Voucher(randomVoucherId, fixedAmountDiscountStrategy);

        FileVoucherDatabase database = new FileVoucherDatabase();
        assertThrows(FileNotReadException.class, () -> database.store(voucher, FILE_PATH));
    }

}