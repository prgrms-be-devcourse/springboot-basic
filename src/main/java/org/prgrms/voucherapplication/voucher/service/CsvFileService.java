package org.prgrms.voucherapplication.voucher.service;

import org.prgrms.voucherapplication.common.VoucherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class CsvFileService {

    private final Logger logger = LoggerFactory.getLogger(CsvFileService.class);
    private static final String FILE_ERROR = "csv 파일 처리 과정에서 오류가 발생했습니다. 확인 후 프로그램을 재실행 시켜주세요.";
    private static final String FILE_DELETE_FAIL = "파일 삭제가 실패했습니다.";

    public List<String> readFileLines(File file) {
        List<String> strings;
        try {
            strings = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            logger.error(FILE_ERROR);
            throw new VoucherException(FILE_ERROR);
        }

        return strings;
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

    public void deleteAll(File file) {
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            logger.error(FILE_DELETE_FAIL);
            throw new FileDeleteException(FILE_DELETE_FAIL);
        }
    }
}
