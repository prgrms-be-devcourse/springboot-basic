package com.programmers.commandLine.domain.voucher.repository.impl;

import com.programmers.commandLine.domain.voucher.entity.FixedAmountVoucher;
import com.programmers.commandLine.domain.voucher.entity.PercentDiscountVoucher;
import com.programmers.commandLine.domain.voucher.entity.Voucher;
import com.programmers.commandLine.domain.voucher.repository.VoucherRepository;
import com.programmers.commandLine.global.factory.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
/**
 *
 *  FileVoucherRepository의 설명을 여기에 작성한다.
 *  MySQL 데이터 베이스에 파일을 저장하고
 *  입출력하는 방식을 상상하면서 구현하였습니다.
 *
 *  @author 장주영
 *  @version 1.0.0
 *  작성일 2022/11/07
 *
**/
@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {

    private final String fileName = "voucherResources";
    private final File file = new File("voucherResources");

    @Override
    public Voucher save(Voucher voucher) {

        LoggerFactory.getLogger().info("FileVoucherRepository save 실행");
        System.out.println("file.exists() : " + file.exists());

        try {
            System.out.println("file.getPath() : " + file.getPath());
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, file.exists()));

            bufferedWriter.write("Id: " + voucher.getVoucherId() +
                    " Type: " + voucher.getType() +
                    " Discount: " + voucher.getDiscount());
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (IOException e) {
            LoggerFactory.getLogger().error("FileVoucherRepository save 에러발생");
        }

        return null;
    }

    @Override
    public Map<String, Voucher> findAll() {
        LoggerFactory.getLogger().info("FileVoucherRepository findAll 실행");

        System.out.println("file = " + file.getPath());
        Map<String, Voucher> voucherMap = new LinkedHashMap<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = bufferedReader.readLine()) !=null ) {
                String[] findLine = line.split(" ");
                Voucher voucher = create(findLine);

                voucherMap.put(voucher.getVoucherId().toString(), voucher);
            }

            bufferedReader.close();
            return voucherMap;

        } catch (IOException e) {
            LoggerFactory.getLogger().error("FileVoucherRepository findAll 에러발생");
            throw new IllegalArgumentException("FileVoucherRepository findAll 에러발생");
        }
    }

    private Voucher create(String[] findLine) {
        String type = findLine[3];
        String discount = findLine[5];

        if (type.equals("fixedAmountVoucher")) {
            return new FixedAmountVoucher(discount);
        } else {
            return new PercentDiscountVoucher(discount);
        }
    }
}
