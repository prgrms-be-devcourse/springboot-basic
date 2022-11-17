package org.prgrms.kdtspringdemo.io.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private final String path;

    public CSVReader(String path) {
        this.path = path;
    }


    public CsvDto readCSV() throws FileNotFoundException, IOException {
        File csv = new File(path);
        BufferedReader bufferedReader = null;
        String line = null;
        if (!csv.exists()) {
            csv.createNewFile();
        }
        List<String[]> resultList = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(csv));
            while ((line = bufferedReader.readLine()) != null) {
                resultList.add(line.split(","));
            }
            return CsvDto.from(resultList);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("파일경로를 찾을 수 없습니다.");
        } catch (IOException e) {
            throw new IOException("입출력 오류가 발생했습니다.");
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                throw new IOException("입출력 오류가 발생했습니다.");

            }
        }
    }
}
