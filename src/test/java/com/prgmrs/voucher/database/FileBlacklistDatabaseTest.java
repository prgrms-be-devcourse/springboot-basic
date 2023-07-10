package com.prgmrs.voucher.database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("블랙리스트 파일 테스트")
class FileBlacklistDatabaseTest {
    @Test
    @DisplayName("정상적으로 블랙리스트 파일을 불러온다.")
    void FileLoadWithoutExceptionTest() {
        final String FILE_PATH = "csv/blacklist.csv";
        FileBlacklistDatabase database = new FileBlacklistDatabase();
        Map<UUID, String> result = database.load(FILE_PATH);

        assertThat(result, not(anEmptyMap()));
    }

    @Test
    @DisplayName("정상적으로 블랙리스트 파일을 불러오지 못한다.")
    void NotExistingFileEmptyReturnTest() {
        final String FILE_PATH = "not/existing.csv";
        FileBlacklistDatabase database = new FileBlacklistDatabase();

        assertThat(database.load(FILE_PATH), is(anEmptyMap()));
    }


}