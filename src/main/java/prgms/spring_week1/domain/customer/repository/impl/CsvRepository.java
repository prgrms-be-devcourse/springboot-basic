package prgms.spring_week1.domain.customer.repository.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.customer.repository.BlackListRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public final class CsvRepository implements BlackListRepository {
    private final String csvFilePath;

    public CsvRepository(@Value("${file.blackList}") String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public List<BlackConsumer> getBlackConsumerList() {
        List<BlackConsumer> blackConsumerList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(csvFilePath)));
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] consumer = line.split(",");
                blackConsumerList.add(new BlackConsumer(consumer[0], consumer[1]));
            }

            return blackConsumerList;
        } catch (IOException e) {
            System.out.println("해당 파일이 존재하지 않습니다");
            return blackConsumerList;
        }
    }
}
