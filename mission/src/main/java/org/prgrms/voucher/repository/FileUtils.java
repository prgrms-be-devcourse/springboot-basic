package org.prgrms.voucher.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static final String FILE_VOUCHER_REPO_PATH = "src/main/resources/voucherFileRepository.txt";

    public static void saveEntity(String voucherInformation) {

        try {
            FileWriter fileWriter = new FileWriter(FILE_VOUCHER_REPO_PATH, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(voucherInformation);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException ioException) {
            logger.error("file io exception : ", ioException);

            throw new IllegalStateException();
        }
    }

    public static List<String> readEntity() {

        try {
            Path path = Paths.get(FILE_VOUCHER_REPO_PATH);

            return Files.readAllLines(path);
        } catch (IOException ioException) {
            logger.error("file io exception : ", ioException);

            throw new IllegalStateException();
        }
    }
}
