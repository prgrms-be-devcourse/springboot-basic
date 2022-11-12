package org.prgrms.kdt.io;

import org.prgrms.kdt.domain.Voucher;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVInOut {

    private final String path;
    private final boolean canAppand;

    public CSVInOut(String path, boolean canAppand) {
        this.path = path;
        this.canAppand = canAppand;
        initFile(path);
    }

    public List<String> readCSV() {
        List<String> csvList = new ArrayList<>();
        File csv = new File(path);
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) {
                csvList.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvList;
    }

    public void writeCSV(Voucher voucher) {
        File csv = new File(path);
        BufferedWriter bw = null; // 출력 스트림 생성
        try {
            csv.createNewFile();
            bw = new BufferedWriter(new FileWriter(csv, canAppand));

            String data = voucher.toString();
            bw.write(data);
            bw.newLine(); // 개행
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initFile(String path) {
        Path filePath = Paths.get(path);
        try {
            Files.deleteIfExists(filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
