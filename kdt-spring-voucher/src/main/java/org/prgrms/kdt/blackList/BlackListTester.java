package org.prgrms.kdt.blackList;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.utill.FileIoStream;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.UUID;

public class BlackListTester {

    public static void main(String[] args) throws IOException {

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        //블랙리스트 파일 생성하고 임의의 데이터 입력
        Customer black1 = new Customer(UUID.randomUUID(),"유지훈","010-1234-5678","경기도 하남시");
        Customer black2 = new Customer(UUID.randomUUID(),"You Ji Hoon","010-1234-5333","경기도 성남시");
        Customer black3 = new Customer(UUID.randomUUID(),"Min","010-4321-8475","서울시 광진구");
        FileIoStream fileIOStream = new FileIoStream();
        fileIOStream.inputCsvFile(black1);
        fileIOStream.inputCsvFile(black2);
        fileIOStream.inputCsvFile(black3);

        //resource를 이용한 블랙리스트 파일 조회
        var resource = applicationContext.getResource("file:customer_blacklist.csv");
        var blackList = Files.readAllLines(resource.getFile().toPath());
        for(String info : blackList){
            String []str = info.split(",");

            System.out.println("customerId : "+str[0]+" name : "+str[1]+" phoneNumber : "+str[2]+" address : "+str[3]);
        }
    }

}
