package devcourse.springbootbasic.util;

import devcourse.springbootbasic.exception.FileErrorMessage;
import devcourse.springbootbasic.exception.FileException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CsvFileHandler<T> {

    private static final String CSV_DELIMITER = ",";
    private final String filePath;

    public CsvFileHandler(String filePath) {
        validateFilePath(filePath);
        this.filePath = filePath;
    }

    private static void validateFilePath(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw FileException.of(FileErrorMessage.FILE_PATH_IS_NULL_OR_EMPTY);
        }
    }

    public List<T> readListFromCsv(Function<String[], T> parser, String csvLineTemplate) {
        List<T> itemList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) break;
                String[] parts = line.split(CSV_DELIMITER);
                if (parts.length != csvLineTemplate.split(CSV_DELIMITER).length) {
                    throw FileException.of(FileErrorMessage.CSV_FIELD_COUNT_MISMATCH);
                }
                itemList.add(parser.apply(parts));
            }
        } catch (IOException e) {
            throw FileException.of(FileErrorMessage.IO_EXCEPTION);
        }

        return itemList;
    }


    public void writeListToCsv(List<T> itemList, Function<T, String> serializer) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (T item : itemList) {
                String csvLine = serializer.apply(item);
                bufferedWriter.write(csvLine);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw FileException.of(FileErrorMessage.IO_EXCEPTION);
        }
    }
}
