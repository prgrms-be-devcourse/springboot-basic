package com.mountain.voucherApp.blacklist;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class BlackListService {

    private final String FILE_PATH = "blackList/customer_blacklist.csv";
    private final File BLACK_LIST_FILE = new FileSystemResource(FILE_PATH).getFile();

    public List<List<String>> readCSVFile() {
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(BLACK_LIST_FILE)) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return records;
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    @PostConstruct
    public void postConstruct() throws IOException {
        if (!BLACK_LIST_FILE.exists()) {
            BLACK_LIST_FILE.getParentFile().mkdir();
            BLACK_LIST_FILE.createNewFile();
            System.out.println("create new file");
        }
    }

}
