package com.prgmrs.voucher.database;

import com.prgmrs.voucher.exception.FileNotReadException;
import com.prgmrs.voucher.model.FixedAmountVoucher;
import com.prgmrs.voucher.model.Voucher;
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
    @DisplayName("정상적 파일 로딩 테스트")
    void FileLoadWithoutExceptionTest() {
        final String FILEPATH = "src/main/csv/vouchers.csv";
        FileVoucherDatabase database = new FileVoucherDatabase();
        Map<UUID, Voucher> result = database.load(FILEPATH);

        assertNotNull(result);
    }

    @Test
    @DisplayName("미존재 파일 로드시 empty 맵 리턴 테스트")
    void NotExistingFileEmptyReturnTest() {
        final String FILEPATH = "src/main/not/existing.csv";
        FileVoucherDatabase database = new FileVoucherDatabase();

        assertThat(database.load(FILEPATH), is(anEmptyMap()));
    }

    @Test
    @DisplayName("정상적 파일 쓰기 테스트")
    void SuccessfulFileWritingTest() {
        final String FILEPATH = "src/main/csv/vouchers.csv";
        UUID randomVoucherId = UUID.randomUUID();
        long amount = 3000;
        Voucher voucher = new FixedAmountVoucher(randomVoucherId, amount);

        FileVoucherDatabase database = new FileVoucherDatabase();

        assertDoesNotThrow(() -> database.store(voucher, FILEPATH));
    }

    @Test
    @DisplayName("미존재 파일 쓰기 에러 테스트")
    void NotExistingFileWritingTest() {
        final String FILEPATH = "src/main/not/existing.csv";
        UUID randomVoucherId = UUID.randomUUID();
        long amount = 3000;
        Voucher voucher = new FixedAmountVoucher(randomVoucherId, amount);

        FileVoucherDatabase database = new FileVoucherDatabase();
        assertThrows(FileNotReadException.class, () -> database.store(voucher, FILEPATH));
    }

}