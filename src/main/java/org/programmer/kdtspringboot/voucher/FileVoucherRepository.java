package org.programmer.kdtspringboot.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

    private final String fileName = "voucherList.txt";
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    @Override
    public Voucher insert(Voucher voucher) {
        String content = voucher.getInfo();
        try (FileWriter fileWriter = new FileWriter(fileName, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            bufferedWriter.write(content);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("voucher 추가 " + voucher);
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        //구현 보류
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> list = new ArrayList<>();
        String content = "";
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            while ((content = bufferedReader.readLine()) != null) {
                String[] contents = content.split(",");
                if(contents[2].equals(VoucherType.FixedAmountVoucher.toString())){
                    list.add(new FixedAmountVoucher(UUID.fromString(contents[0]), Long.parseLong(contents[1])));
                }else if(contents[2].equals(VoucherType.PercentDiscountVoucher.toString())){
                    list.add(new PercentDiscountVoucher(UUID.fromString(contents[0]), Long.parseLong(contents[1])));
                }else{
                    logger.info("File 읽는 도중 알수없는 타입 발견");
                    throw new IOException();
                }
            }
            logger.info("File list 반환 성공");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        //구현 보류
        return Optional.empty();
    }

    @Override
    public void deleteAll() {
        //구현 보류
    }
}
