package prgms.spring_week1.domain.customer.repository.impl;

import org.springframework.stereotype.Repository;
import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.customer.repository.BlackListRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CsvRepository implements BlackListRepository {

    @Override
    public List<BlackConsumer> getBlackConsumerList(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("/home/osh/Downloads/java_week1/spring_week1/src/main/resources/BlackListCsv.csv")));
            List<BlackConsumer> blackConsumerList = new ArrayList<>();
            String line = "";
            while((line = br.readLine()) != null){
                String[] consumer = line.split(",");
                blackConsumerList.add(new BlackConsumer(consumer[0],consumer[1]));
            }
            return blackConsumerList;
        } catch (IOException e){
            System.out.println("해당 파일이 존재하지 않습니다");
            return null;
        }
    }
}
