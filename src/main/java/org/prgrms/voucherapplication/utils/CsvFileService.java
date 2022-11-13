package org.prgrms.voucherapplication.utils;

import org.prgrms.voucherapplication.common.VoucherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Component
public class CsvFileService {

    private final Logger logger = LoggerFactory.getLogger(CsvFileService.class);
    public static final String FILE_ERROR = "csv 파일 처리 과정에서 오류가 발생했습니다. 확인 후 프로그램을 재실행 시켜주세요.";

    public String readFileLines(File file) {
        List<String> strings;
        try {
            strings = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            logger.error(FILE_ERROR);
            throw new VoucherException(FILE_ERROR);
        }

        StringBuilder blacklist = new StringBuilder();
        for (String string : strings) {
            String[] split = string.split(",");
            blacklist.append(split[0]).append(". ").append(split[1]).append(" ");
        }

        return blacklist.toString();
    }

    public void write(File file, String line) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(line);
            writer.close();
        } catch (IOException e) {
            logger.error(FILE_ERROR);
            throw new VoucherException(FILE_ERROR);
        }
    }
}
