package org.prgrms.kdt.fileutil;

import org.prgrms.kdt.blacklist.Person;
import org.prgrms.kdt.domain.Voucher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "file")
public class FileIOStream implements InitializingBean {

    private static String voucherName;

    private static String customerName;

    public void fileInputStream(Voucher v) throws IOException {
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        String fileName = tempDir + File.separator +voucherName;
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));
        bw.write(v+"\r\n");
        bw.flush();
    }


    public List<String> fileOutputStream() throws IOException {
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        List<String> list = new ArrayList<>();
//        String fileName = voucherName;
        String fileName = tempDir + File.separator +voucherName;
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
        String fileName = customerName;
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));
        String str = person.toString();
        // 이름, 전화번호, 사는곳
        bw.write( str+"\r\n");
        bw.flush();
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        System.out.println(MessageFormat.format("[FileIOStream] voucherName -> {0}",voucherName));
//        System.out.println(MessageFormat.format("[FileIOStream] customerName -> {0}",customerName));
    }
}
