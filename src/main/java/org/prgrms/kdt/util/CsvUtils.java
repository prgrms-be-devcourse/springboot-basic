package org.prgrms.kdt.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvUtils {

    private CsvUtils() {
    }

    public static List<List<String>> readCsv(String path, String fileName) throws IOException {
        List<List<String>> csvList = new ArrayList<>();
        String filePath = path + "\\" + fileName;
        File csvFile = new File(filePath);
        String row;
        BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
        while((row = csvReader.readLine()) != null){
            String[] data = row.split(",");
            csvList.add(Arrays.asList(data));
        }
        return csvList;
    }

    public static void writeCsv(String path, String fileName, String data) throws IOException{
        String filePath = path + "\\" + fileName;
        File csvFile = new File(filePath);
        if(!csvFile.exists()){
            csvFile.createNewFile();
        }
        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(csvFile, true));
        csvWriter.write(data);
        csvWriter.newLine();
        csvWriter.close();
    }
}
