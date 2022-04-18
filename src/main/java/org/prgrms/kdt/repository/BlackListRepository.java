package org.prgrms.kdt.repository;

import org.prgrms.kdt.service.BlackListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

@Repository
public class BlackListRepository {

    private ApplicationContext applicationContext;
    private static final Logger logger = LoggerFactory.getLogger(BlackListService.class);

    @Autowired
    public BlackListRepository(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public List<String> getBlackListByFile() {
        var resource = applicationContext.getResource("classpath:customer_blacklist.csv");
        List<String> strings;
        ArrayList<String> blackList = new ArrayList<>();
        try {
            strings = Files.readAllLines(resource.getFile().toPath());
            strings.forEach(s -> blackList.addAll(Arrays.stream(s.split(",")).toList()));
        } catch (Exception e) {
            logger.warn("Exception occur -> {}", e.getMessage());
        }
        return blackList;
    }
}
