package org.prgrms.kdt.storage;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Profile("dev")
public class FileVoucherStorage implements VoucherStorage {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherStorage.class);
    private static final String filePath = "./voucherList.txt";

    @Override
    public void saveVoucher(Voucher newVoucher) {
        File file = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            String voucherData = String.format("%s,%s,%d%n", newVoucher.getVoucherName(), newVoucher.getVoucherId().toString(), newVoucher.getVoucherDiscountValue());
            fileWriter.write(voucherData);
        } catch (IOException e) {
            logger.error("An error occurred while writing the file : {}", e.getMessage());
        }
    }

    @Override
    public List<Voucher> findAllVoucher() {
        File file = new File(filePath);
        List<Voucher> voucherList = new ArrayList<>();
        readVoucherListFromFile(file, voucherList);
        return voucherList;
    }

    private static void readVoucherListFromFile(File file, List<Voucher> voucherList) {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader);) {
            String voucherDataReadLine = "";
            while ((voucherDataReadLine = bufferedReader.readLine()) != null) {
                String[] splitVoucherData = voucherDataReadLine.split(",");
                VoucherKind voucherKind = VoucherKind.findVoucherKindByFile(splitVoucherData[0]);
                addVoucherToList(voucherList, splitVoucherData, voucherKind);
            }
        } catch (FileNotFoundException e) {
            logger.error("Excpetion Message : {}", e.getMessage());
        } catch (IOException e) {
            logger.error("Exception Message : {}", e.getMessage());
        }
    }

    private static void addVoucherToList(List<Voucher> voucherList, String[] splitVoucherData, VoucherKind voucherKind) {
        switch (voucherKind) {
            case FIXED_AMOUNT_VOUCHER ->
                voucherList.add(new FixedAmountVoucher(UUID.fromString(splitVoucherData[1]), Long.parseLong(splitVoucherData[2])));
            case PERCENT_DISCOUNT_VOUCHER ->
                voucherList.add(new PercentDiscountVoucher(UUID.fromString(splitVoucherData[1]), Long.parseLong(splitVoucherData[2])));
        }
    }
}
