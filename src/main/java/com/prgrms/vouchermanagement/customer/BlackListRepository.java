package com.prgrms.vouchermanagement.customer;

import com.prgrms.vouchermanagement.util.FilePathProperties;
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

    private final FilePathProperties filePathProperties;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final List<Customer> blackList = new ArrayList<>();

    public BlackListRepository(FilePathProperties filePathProperties) {
        this.filePathProperties = filePathProperties;
    }

    @PostConstruct
    private void init() {
        String blackListFilePath = filePathProperties.getBlackListFilePath();

        // customer_black_list.csv 파일을 읽어서 Member 타입의 List 로 변환한다.
        try (BufferedReader br = new BufferedReader(new FileReader(blackListFilePath))) {
            String name = null;
            while ((name = br.readLine()) != null) {
                blackList.add(new Customer(null, name,null ,null, null));
            }
            log.info("init blackList. size={}", blackList.size());
        } catch (IOException e) {
            log.error("not found {}", blackListFilePath, e);
        }
    }

    public List<Customer> findAll() {
        log.info("find all blackList. size={}", blackList.size());
        return blackList;
    }
}
