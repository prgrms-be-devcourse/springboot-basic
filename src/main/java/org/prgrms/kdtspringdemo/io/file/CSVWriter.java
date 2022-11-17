package org.prgrms.kdtspringdemo.io.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {
    private final String path;
    private final boolean append;

    public CSVWriter(String path, boolean append) {
        this.path = path;
        this.append = append;
    }

    public boolean isWriterAppendMode() {
        return append;
    }

    public void writeCSV(CsvDto csvDto) throws IllegalStateException {
        File targetCsv = new File(path);
        List<String[]> value = csvDto.value;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(targetCsv, append))) {
            for (String[] strings : value) {
                String line = String.join(",", strings);
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new IllegalStateException("파일 입출력 에러가 발생했습니다.");
        }
    }
}
