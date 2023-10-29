package com.programmers.vouchermanagement.utils;

import com.programmers.vouchermanagement.message.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvFileUtil {
    private static final Logger logger = LoggerFactory.getLogger(CsvFileUtil.class);

    public static List<String> readCsvFile(String filePath) {
        String line;
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            logger.warn(MessageFormat.format("{0} : {1}", ErrorMessage.FILE_NOT_FOUND_MESSAGE.getMessage(), filePath));
        } catch (IOException e) {
            logger.error("Error occurred at FileReader: ", e);
        }
        return lines;
    }

    public static <T extends CsvConvertable> void updateCsvFile(String filePath, List<T> items) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            items.stream()
                    .map(voucher -> voucher.joinInfo(","))
                    .forEach(line -> {
                        try {
                            bw.write(line);
                            bw.newLine();
                        } catch (IOException e) {
                            logger.error("Error occurred at FileWriter: ", e);
                        }
                    });
        } catch (IOException e) {
            logger.error("Error occurred af FileWriter: ", e);
        }
    }
}
