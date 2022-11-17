package org.prgrms.kdt.service;

import org.prgrms.kdt.dao.entity.BlackList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Service
public class BlackListService {

    private static final Logger logger = LoggerFactory.getLogger(BlackListService.class);
    private final String filePath;
    private final ClassPathResource resource;
    private BufferedReader bufferedReader;

    public BlackListService(@Value("${kdt.blacklist-file}") String filePath) {
        this.filePath = filePath;
        this.resource = new ClassPathResource(filePath);
    }

    private BlackList createBlackList(String name, String birthDate) throws IllegalArgumentException {
        return new BlackList(name, Date.valueOf(birthDate));
    }

    public List<BlackList> getAllBlackList() {
        List<BlackList> blackLists = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(resource.getFile()));
            String line = bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String[] format = line.split(",");

                BlackList blackList = createBlackList(format[0], format[1]);

                blackLists.add(blackList);
            }
        } catch (IOException e) {
            logger.error("{} {}", e.getMessage(), e.getStackTrace());
        } catch (IllegalArgumentException e) {
            logger.error("{} {}", "blacklist.csv 파일의 date 형식이 잘못되었습니다.", e.getStackTrace());
        }

        return blackLists;
    }
}
