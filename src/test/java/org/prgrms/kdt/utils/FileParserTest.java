package org.prgrms.kdt.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.kdt.exceptions.AmountException;
import org.prgrms.kdt.io.FileIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FileParserTest {

    private static final Logger logger = LoggerFactory.getLogger(FileParserTest.class);

    private final FileIO fileIO = new FileIO();
    private final FileParser fileParser = new FileParser(fileIO);
    private static String exceptionVoucherId;
    private static String exceptionVoucherFilePath;

    @BeforeAll
    static void setup(){
        exceptionVoucherId = UUID.randomUUID().toString();
        exceptionVoucherFilePath = MessageFormat.format("src/main/resources/vouchers/{0}.txt", exceptionVoucherId);
        String exceptionVoucherInfo = MessageFormat.format("{0}/FixedAmountVoucher/50000", exceptionVoucherId);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(exceptionVoucherFilePath))) {
            bufferedWriter.write(exceptionVoucherInfo);
            bufferedWriter.flush();
        } catch (IOException fileWriteException) {
            logger.error("테스트 파일 생성에 실패하였습니다.");
        }
    }

    @Test
    @DisplayName("파일 내의 잘못된 내용이 있을 경우 예외를 반환한다.")
    void testFileException(){
        assertThrows(AmountException.class, () -> fileParser.getVoucherById(exceptionVoucherId));
    }

    @AfterAll
    static void cleanup(){
        File exceptionFile = new File(exceptionVoucherFilePath);
        exceptionFile.delete();
    }
}
