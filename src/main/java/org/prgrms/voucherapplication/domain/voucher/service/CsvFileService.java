package org.prgrms.voucherapplication.domain.voucher.service;

import org.prgrms.voucherapplication.domain.voucher.exception.FileDeleteException;
import org.prgrms.voucherapplication.domain.voucher.exception.FileReadException;
import org.prgrms.voucherapplication.domain.voucher.exception.FileWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.prgrms.voucherapplication.global.exception.ExceptionCode.FILE_DELETE_FAIL;
import static org.prgrms.voucherapplication.global.exception.ExceptionCode.FILE_IO_ERROR;

@Service
public class CsvFileService {

    private final Logger logger = LoggerFactory.getLogger(CsvFileService.class);

    public List<String> readFileLines(File file) {
        List<String> strings;
        try {
            strings = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            logger.info(FILE_IO_ERROR.getMessege());
            throw new FileReadException(FILE_IO_ERROR);
        }

        return strings;
    }

    public void write(File file, String line) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(line);
            writer.close();
        } catch (IOException e) {
            logger.info(FILE_IO_ERROR.getMessege());
            throw new FileWriteException(FILE_IO_ERROR);
        }
    }

    public void deleteAll(File file) {
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            logger.info(FILE_DELETE_FAIL.getMessege());
            throw new FileDeleteException(FILE_DELETE_FAIL);
        }
    }
}
