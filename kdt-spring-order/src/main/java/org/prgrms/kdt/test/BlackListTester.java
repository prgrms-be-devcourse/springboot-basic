package org.prgrms.kdt.test;

import org.prgrms.kdt.blacklist.Person;
import org.prgrms.kdt.configuration.AppConfiguration;
import org.prgrms.kdt.fileutil.FileIOStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class BlackListTester {

    public static void main(String[] args) throws IOException {

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        //블랙리스트 파일 생성하고 임의의 데이터 입력
        Person person1 = new Person(UUID.randomUUID(),"유지훈","010-1234-5678","경기도 하남시");
        Person person2 = new Person(UUID.randomUUID(),"You Ji Hoon","010-1234-5333","경기도 성남시");
        Person person3 = new Person(UUID.randomUUID(),"Min","010-4321-8475","서울시 광진구");
        FileIOStream fileIOStream = new FileIOStream();
        fileIOStream.inputCsvFile(person1);
        fileIOStream.inputCsvFile(person2);
        fileIOStream.inputCsvFile(person3);

        //resource를 이용한 블랙리스트 파일 조회
        var resource = applicationContext.getResource("file:customer_blacklist.csv");
        var blackList = Files.readAllLines(resource.getFile().toPath());
        for(String info : blackList){
            String []str = info.split(",");
            System.out.println("personId : "+str[0]+" name : "+str[1]+" phoneNumber : "+str[2]+" address : "+str[3]);
        }
    }
}
