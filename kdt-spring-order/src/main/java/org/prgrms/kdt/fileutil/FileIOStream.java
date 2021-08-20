package org.prgrms.kdt.fileutil;

import lombok.Getter;
import lombok.Setter;
import org.prgrms.kdt.blacklist.Person;
import org.prgrms.kdt.domain.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
public class FileIOStream {

    @Value("${file.voucher-name}")
    private String voucherName;

    @Value("${file.customer-name}")
    private String customerName;

    public void fileInputStream(Voucher v) throws IOException {
        String fileName = "voucher_file.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));
        bw.write(v+"\r\n");
        bw.flush();
    }

    public List<String> fileOutputStream() throws IOException {
        List<String> list = new ArrayList<>();
        String fileName = "voucher_file.txt";
        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
        String v = null;
        while ((v = fileReader.readLine()) != null){
            list.add(v);
        }
        fileReader.close();
        return list;
    }

    //인자로 블랙리스트 정보들을 받아 파일을 생성한다.
    public void inputCsvFile(Person person) throws IOException {
        String fileName = "customer_blacklist.csv";
//        System.out.println(fileName);
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));
        String str = person.toString();
        // 이름, 전화번호, 사는곳
        bw.write( str+"\r\n");
        bw.flush();
    }

    //csv파일을 읽어들인다.
    public void getCsvFile(){
    }

}
