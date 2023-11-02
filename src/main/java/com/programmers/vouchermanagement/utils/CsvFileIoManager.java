package com.programmers.vouchermanagement.utils;

import com.programmers.vouchermanagement.exception.FileIOException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvFileIoManager {

    public List<String> readCsv(File file) {

        List<String> dataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String data;

            while ((data = br.readLine()) != null) {

                dataList.add(data);
            }
        } catch (FileNotFoundException e) {
            throw new FileIOException("File not found.");

        } catch (IOException e) {
            throw new FileIOException("File not read due to file issue.");
        }

        return dataList;
    }

    public void writeCsv(File file, String data) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {

            bw.write(data);
            bw.newLine();

        } catch (IOException e) {
            throw new FileIOException("File not wrote due to file issue.");
        }
    }

    public void updateCsv(File file, List<String> dataList) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            for (String singleData : dataList) {

                bw.write(singleData);
                bw.newLine();
            }

        } catch (IOException e) {
            throw new FileIOException("File not updated due to file issue.");
        }
    }
}
