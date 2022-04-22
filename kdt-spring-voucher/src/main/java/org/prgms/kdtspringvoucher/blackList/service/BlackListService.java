package org.prgms.kdtspringvoucher.blackList.service;

import org.prgms.kdtspringvoucher.blackList.domain.BlackList;
import org.prgms.kdtspringvoucher.blackList.repository.BlackListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListService {
    private static final Logger logger = LoggerFactory.getLogger(BlackListService.class);
    private final BlackListRepository blackListRepository;

    public BlackListService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public void showAllBlackList() {
        List<BlackList> blackList = blackListRepository.findAll();
        if (blackList.isEmpty())
            System.out.println("No blackList.....\n");
        else {
            logger.info("Succeed show all blackList in repository");
            blackList.forEach(System.out::println);
        }
    }
}
