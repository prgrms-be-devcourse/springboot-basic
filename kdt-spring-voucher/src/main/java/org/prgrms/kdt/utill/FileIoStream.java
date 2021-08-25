package org.prgrms.kdt.utill;

import org.prgrms.kdt.domain.Customer;
import org.prgrms.kdt.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "kdt.file")
public class FileIoStream implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(FileIoStream.class);

    private static String voucherName;

    private static String customerName;

    public void fileInputStream(Voucher v){
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        String fileName = tempDir + File.separator +voucherName;
        System.out.println(fileName);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));
            bw.write(v+"\r\n");
            bw.flush();
        }catch (IOException io){
            logger.error("파일 입력 오류 발생 ");
        }
    }

    public List<String> fileOutputStream(){
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        List<String> list = new ArrayList<>();
        String fileName = tempDir + File.separator +voucherName;
        System.out.println(fileName);
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
            String v = null;
            while ((v = fileReader.readLine()) != null){
                list.add(v);
            }
            fileReader.close();
        }catch (IOException io){
            logger.error("파일 출력 오류 발생 ");
        }
        return list;
    }

    //인자로 블랙리스트 정보들을 받아 파일을 생성한다.
    public void inputCsvFile(Customer customer,String CsvFileName){
        String fileName = CsvFileName;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));
            String str = customer.toString();
            // 이름, 전화번호, 사는곳
            bw.write( str+"\r\n");
            bw.flush();
        }catch (IOException io){
            logger.error("csv파일 입력 오류 발생 ");
        }
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
