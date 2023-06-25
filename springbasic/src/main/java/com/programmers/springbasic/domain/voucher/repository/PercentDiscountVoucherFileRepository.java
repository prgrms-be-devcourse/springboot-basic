package com.programmers.springbasic.domain.voucher.repository;

import com.programmers.springbasic.domain.voucher.entity.PercentDiscountVoucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PercentDiscountVoucherFileRepository implements VoucherRepository<PercentDiscountVoucher, UUID> {
    private static final Logger logger = LoggerFactory.getLogger(PercentDiscountVoucher.class);
    private static final String PERCENT_DISCOUNT_VOUCHER_FILE_PATH = "./vouchers/percentDiscountVouchers.csv";

    @Override
    public void save(PercentDiscountVoucher voucher) {
        Map<UUID, PercentDiscountVoucher> percentDiscountVouchers = readVouchersFromFile();

        percentDiscountVouchers.put(voucher.getCode(), voucher);
        writeVouchersFromFile(percentDiscountVouchers);
    }

    @Override
    public List<PercentDiscountVoucher> findAll() {
        Map<UUID, PercentDiscountVoucher> percentDiscountVouchers = readVouchersFromFile();

        return percentDiscountVouchers.values()
                .stream()
                .collect(Collectors.toList());
    }

    private Map<UUID, PercentDiscountVoucher> readVouchersFromFile() {
        Map<UUID, PercentDiscountVoucher> percentDiscountVouchers = null;

        try {
            FileInputStream fileIn = new FileInputStream(PERCENT_DISCOUNT_VOUCHER_FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            percentDiscountVouchers = (Map<UUID, PercentDiscountVoucher>) in.readObject();

            in.close();
            fileIn.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        if (percentDiscountVouchers != null) {
            return percentDiscountVouchers;
        } else {
            return new HashMap<>();   // 비어 있다면 비어 있는 Map을 반환
        }
    }

    private void writeVouchersFromFile(Map<UUID, PercentDiscountVoucher> percentDiscountVouchers) {
        try {
            FileOutputStream fileOut = new FileOutputStream(PERCENT_DISCOUNT_VOUCHER_FILE_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(percentDiscountVouchers);

            out.close();
            fileOut.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
