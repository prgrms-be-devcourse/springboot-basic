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

    public static void saveEntity(String voucherInformation, String voucherFilePath) {

        try (
            FileWriter fileWriter = new FileWriter(voucherFilePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {

            bufferedWriter.write(voucherInformation);
            bufferedWriter.newLine();
        } catch (IOException ioException) {
            logger.error("file io exception : ", ioException);

            throw new IllegalStateException();
        }
    }

    public static List<String> readEntities(String voucherFilePath) {

        try {
            Path path = Paths.get(voucherFilePath);

            return Files.readAllLines(path);
        } catch (IOException ioException) {
            logger.error("file io exception : ", ioException);

            throw new IllegalStateException();
        }
    }

    public static void saveIdSequence(Long voucherId, String idSequencePath) {

        try (
                FileWriter fileWriter = new FileWriter(idSequencePath);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {

            bufferedWriter.write(voucherId.toString());
            bufferedWriter.newLine();
        } catch (IOException ioException) {
            logger.error("file io exception : ", ioException);

            throw new IllegalStateException();
        }
    }

    public static Long getLastId(String idSequencePath) {

        try (FileReader fileReader = new FileReader(idSequencePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)){
            String idInformation = bufferedReader.readLine();

            return idInformation == null ? 0 : Long.parseLong(idInformation);
        } catch (IOException ioException) {
            logger.error("file io exception : ", ioException);

            throw new IllegalStateException();
        }
    }
}
