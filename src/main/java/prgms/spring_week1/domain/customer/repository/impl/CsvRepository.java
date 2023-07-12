package prgms.spring_week1.domain.customer.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.customer.repository.BlackListRepository;
import prgms.spring_week1.domain.voucher.service.VoucherService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvRepository implements BlackListRepository {
    private final Logger logger = LoggerFactory.getLogger(CsvRepository.class);
    private final String csvFilePath;

    private final String DELIMITER = ",";

    public CsvRepository(@Value("${file.blackList}") String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public List<BlackConsumer> getBlackConsumerList() {
        List<String> csvList = null;
        List<BlackConsumer> blackConsumerList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(csvFilePath)));
            csvList = br.lines().toList();
        } catch (IOException e) {
            logger.warn("해당 파일이 존재하지 않습니다");
            return blackConsumerList;
        }

        for (String consumerLine : csvList) {
            String[] consumer = consumerLine.split(DELIMITER);
            blackConsumerList.add(new BlackConsumer(consumer[0], consumer[1]));
        }

        return blackConsumerList;
    }
}
