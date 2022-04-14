package com.prgrms.vouchermanagement.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BlackListRepository {
    private static final String BLACK_LIST_FILE_NAME = "customer_black_list.csv";
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final List<Customer> blackList = new ArrayList<>();

    @PostConstruct
    private void init() {
        // customer_black_list.csv 파일을 읽어서 Member 타입의 List 로 변환한다.
        try (BufferedReader br = new BufferedReader(new FileReader(BLACK_LIST_FILE_NAME))) {
            String name = null;
            while ((name = br.readLine()) != null) {
                blackList.add(new Customer(name));
            }
            log.info("init blackList. size={}", blackList.size());
        } catch (IOException e) {
            log.error("not found {}", BLACK_LIST_FILE_NAME, e);
        }
    }

    public List<Customer> findAll() {
        log.info("find all blackList. size={}", blackList.size());
        return blackList;
    }
}
