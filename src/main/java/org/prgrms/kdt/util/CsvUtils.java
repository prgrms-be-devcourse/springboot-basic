package org.prgrms.kdt.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvUtils {

    private CsvUtils() {
    }

    public static List<List<String>> readCsv(String path, String fileName){
        List<List<String>> csvList = new ArrayList<>();
        String filePath = path + "\\" + fileName;
        String row;
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
            while((row = csvReader.readLine()) != null){
                String[] data = row.split(",");
                csvList.add(Arrays.asList(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvList;
    }

    public static void writeCsv(String path, String fileName, String data) {
        String filePath = path + "\\" + fileName;
        File csvFile = new File(filePath);
        try {
            if(!csvFile.exists()){
                csvFile.createNewFile();
            }
            BufferedWriter csvWriter = new BufferedWriter(new FileWriter(csvFile, true));
            System.out.println(data);
            csvWriter.write(data);
            csvWriter.newLine();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
