package org.programmer.kdtspringboot.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

    private final String fileName= "voucherList.txt";;
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    @Override
    public void saveVoucher(Voucher voucher) {
        String content = voucher.getVoucherId() + "," + voucher.getValue() + "," + voucher.getClass().toString();
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("voucher 추가(" + voucher.getVoucherId() + "," + voucher.getValue() + "," + voucher.getClass().toString() + ")");
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String content = null;
            while ((content = bufferedReader.readLine()) != null) {
                String[] contents = content.split(",");
                if (contents[2].equals("FixedAmountVoucher")) {
                    list.add(new FixedAmountVoucher(UUID.fromString(contents[0]), Long.parseLong(contents[1])));
                } else if (contents[2].equals("PercentDiscountVoucher")) {
                    list.add(new PercentDiscountVoucher(UUID.fromString(contents[0]), Long.parseLong(contents[1])));
                }
            }
            logger.info("File list 반환 성공");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
