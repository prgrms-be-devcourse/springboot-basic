package org.programmers.springbootbasic.repository;

import org.programmers.springbootbasic.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerBlackListRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerBlackListRepository.class);
    private final File csv;
    private static final int BLACKLIST_ID_INDEX = 0;
    private static final int BLACKLIST_NAME_INDEX = 1;

    public CustomerBlackListRepository(@Value("${files.location.blacklist}") String CSV_FILE_PATH) {
        this.csv = new File(CSV_FILE_PATH);
    }

    public List<Customer> findAll() {
        return readBlacklistsFromFile();
    }

    private List<Customer> readBlacklistsFromFile() {
        List<Customer> blackList = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader(csv));
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] blackInfo = line.split(", ");
                blackList.add(assembleBlackList(blackInfo));
            }
        } catch (FileNotFoundException e) {
            logger.error("읽어올 파일을 찾을 수 없습니다.");
        } catch (IOException e) {
            logger.error("블랙리스트를 읽어올 수 없습니다.");
        }
        return blackList;
    }

    private Customer assembleBlackList(String[] blackListInfo) {
        return new Customer(Long.parseLong(blackListInfo[BLACKLIST_ID_INDEX]), blackListInfo[BLACKLIST_NAME_INDEX]);
    }
}
