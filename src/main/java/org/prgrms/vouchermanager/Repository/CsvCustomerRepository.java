package org.prgrms.vouchermanager.Repository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CsvCustomerRepository implements CustomerRepositroy{

    String csvFilePath = "D:\\Users\\programmers\\vouchermanager\\src\\main\\java\\org\\prgrms\\vouchermanager\\Repository\\blacklist.csv";
    List<List<String>> result = new ArrayList<>();
    BufferedReader br = null;


    @Override
    public List<List<String>> findAll() {
        try{
            br = Files.newBufferedReader(Paths.get(csvFilePath));
            String line = "";
            while((line = br.readLine()) != null){
                List<String> stringList = new ArrayList<>();
                String arr[] = line.split(",");

                stringList = Arrays.asList(arr);
                result.add(stringList);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}
