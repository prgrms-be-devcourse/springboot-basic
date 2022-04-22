package com.pppp0722.vouchermanagement.voucher.repository;

import com.pppp0722.vouchermanagement.voucher.model.FixedAmountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.PercentDiscountVoucher;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    @Value("${csv.voucher.file-path}")
    private String filePath;

    // csv 파일에 voucher 입력
    @Override
    public void insert(Voucher voucher) {
        File file = new File(filePath);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            String voucherType = "";
            switch (voucher.getVoucherType()) {
                case FIXED_AMOUNT:
                    voucherType = "FIXED_AMOUNT";
                    break;
                case PERCENT_DISCOUNT:
                    voucherType = "PERCENT_DISCOUNT";
                    break;
                default:
                    break;
            }
            String toWrite = voucher.getVoucherId() + "," + voucherType + "," + voucher.getAmount();
            bufferedWriter.write(toWrite);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("There's no file at {}.", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IO error!");
        }
    }

    // csv 파일에서 모든 voucher 가져오기
    @Override
    public List<Voucher> getVoucherList() {
        List<Voucher> voucherList = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(",");
                UUID voucherID = UUID.fromString(splitLine[0]);
                VoucherType voucherType = VoucherType.getVoucherType(splitLine[1]);
                long amount = Long.parseLong(splitLine[2]);

                Voucher voucher = null;
                switch(voucherType) {
                    case FIXED_AMOUNT:
                        voucher = new FixedAmountVoucher(voucherID, amount);
                        break;
                    case PERCENT_DISCOUNT:
                        voucher = new PercentDiscountVoucher(voucherID, amount);
                        break;
                    default:
                        logger.error("Invalid voucher type = {}", splitLine[1]);
                        break;
                }

                voucherList.add(voucher);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("There's no file at {}.", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Can't read line from {}.", filePath);
        }

        return voucherList;
    }
}
