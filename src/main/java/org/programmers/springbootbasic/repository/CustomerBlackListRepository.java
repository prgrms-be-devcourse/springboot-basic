package org.programmers.springbootbasic.repository;

import org.programmers.springbootbasic.domain.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerBlackListRepository {

    private final File csv;
    private static final int BLACKLIST_ID_INDEX = 0;
    private static final int BLACKLIST_NAME_INDEX = 1;

    public CustomerBlackListRepository(@Value("${files.location.blacklist}") String CSV_FILE_PATH) {
        this.csv = new File(CSV_FILE_PATH);
    }

    public List<Customer> findAll() {
        return read();
    }

    private List<Customer> read() {
        List<Customer> blackList = new ArrayList<>();
        BufferedReader br = null;
        try {
            String line;
            br = new BufferedReader(new FileReader(csv));
            while((line = br.readLine()) != null) {
                String[] blackInfo = line.split(", ");
                blackList.add(assembleBlackList(blackInfo));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return blackList;
    }

    private Customer assembleBlackList(String[] blackListInfo) {
        return new Customer(Long.parseLong(blackListInfo[BLACKLIST_ID_INDEX]), blackListInfo[BLACKLIST_NAME_INDEX]);
    }
}
