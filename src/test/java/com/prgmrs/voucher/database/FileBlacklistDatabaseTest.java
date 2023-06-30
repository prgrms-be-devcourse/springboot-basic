package com.prgmrs.voucher.database;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("블랙리스트 파일 테스트")
class FileBlacklistDatabaseTest {
    @Test
    @DisplayName("정상적 파일 로딩 테스트")
    void FileLoadWithoutExceptionTest() {
        final String FILEPATH = "src/main/csv/blacklist.csv";
        FileBlacklistDatabase database = new FileBlacklistDatabase();
        Map<UUID, String> result = database.load(FILEPATH);

        assertNotNull(result);
    }

    @Test
    @DisplayName("미존재 파일 로드시 empty 맵 리턴 테스트")
    void NotExistingFileEmptyReturnTest() {
        final String FILEPATH = "src/main/not/existing.csv";
        FileBlacklistDatabase database = new FileBlacklistDatabase();

        assertThat(database.load(FILEPATH), is(anEmptyMap()));
    }


}