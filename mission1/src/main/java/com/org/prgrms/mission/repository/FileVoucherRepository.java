package com.org.prgrms.mission.repository;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.org.prgrms.mission.model.FixedAmoutVoucher;
import com.org.prgrms.mission.model.PercentDiscountVoucher;
import com.org.prgrms.mission.model.Voucher;
import com.org.prgrms.mission.model.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {


    private final String filePath = "./voucher.csv";

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));  // csv 파일 읽어오기

            while ((line = br.readLine()) != null) {

                String[] token = line.replace("\"","").split(","); // 문자열중 " 제거 및 , 기준으로 나누기
                UUID id = UUID.fromString(token[1]);
                long num = Long.parseLong(token[2]);


                    if (token[0].equals("FIXED_AMOUNT")) {
                        vouchers.add(new FixedAmoutVoucher(VoucherType.FIXED_AMOUNT,id,num));


                    }
                    else if (token[0].equals("PERCENT_DISCOUNT")) {
                        vouchers.add(new PercentDiscountVoucher(VoucherType.PERCENT_DISCOUNT,id, num));

                    }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return vouchers;
    }

    @Override
    public Voucher insert(Voucher voucher) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        FileWriter writer = new FileWriter(filePath,true);
        StatefulBeanToCsv<Voucher> beanToCsv = new StatefulBeanToCsvBuilder<Voucher>(writer).build(); //bean 객체를 바로 CSV로 변환
        beanToCsv.write(List.of(voucher));
        writer.close();


        return voucher;
    }
}
