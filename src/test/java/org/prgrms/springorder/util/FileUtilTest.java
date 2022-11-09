package org.prgrms.springorder.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileUtilTest {

    private final String path = "test/";
    private final String fileName = "testfile.csv";

    @AfterEach
    void afterEach() {
        File file = new File(path + fileName);

        if (file.exists()) {
            file.delete();
        }
    }

    @DisplayName("파일 생성 테스트 - 파일이 없다면 파일이 생성된다.")
    @Test
    void createFileTest() {
        //given
        File notExistsFile = new File(path + fileName);

        boolean fileExists = notExistsFile.exists();

        //when
        File file = FileUtil.createFile(path, fileName);

        boolean createdFileExists = file.exists();

        //then
        assertFalse(fileExists);
        assertTrue(createdFileExists);
    }

    @DisplayName("파일 삭제 테스트 - 파일이 존재하면 파일이 삭제된다.")
    @Test
    void deleteFileTest() throws IOException {
        //given
        File existsFile = new File(path + fileName);

        if (!existsFile.getParentFile().exists()) {
            existsFile.getParentFile().mkdirs();
            existsFile.createNewFile();
        }

        //when
        FileUtil.deleteFile(existsFile);

        //then
        assertFalse(existsFile.exists());
    }

}