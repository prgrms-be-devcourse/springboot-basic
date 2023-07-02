package com.programmers.springbasic.domain.voucher.repository;

import com.programmers.springbasic.domain.voucher.entity.Voucher;
import com.programmers.springbasic.domain.voucher.model.VoucherOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class VoucherFileRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileRepository.class);

    @Value("${voucher.fixed-amount-file-path}")
    private String FIXED_AMOUNT_VOUCHER_FILE_PATH;
    @Value("${voucher.percent-discount-file-path}")
    private String PERCENT_DISCOUNT_VOUCHER_FILE_PATH;

    @Override
    public void save(Voucher voucher) {
        VoucherOption voucherType = voucher.getVoucherType();

        switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER: {
                Map<UUID, Voucher> fixedAmountVouchers = readVouchersFromFile(FIXED_AMOUNT_VOUCHER_FILE_PATH);

                fixedAmountVouchers.put(voucher.getCode(), voucher);
                writeVouchersFromFile(fixedAmountVouchers, FIXED_AMOUNT_VOUCHER_FILE_PATH);
                break;
            }
            case PERCENT_DISCOUNT_VOUCHER: {
                Map<UUID, Voucher> percentDiscountVouchers = readVouchersFromFile(PERCENT_DISCOUNT_VOUCHER_FILE_PATH);

                percentDiscountVouchers.put(voucher.getCode(), voucher);
                writeVouchersFromFile(percentDiscountVouchers, PERCENT_DISCOUNT_VOUCHER_FILE_PATH);
                break;
            }
        }
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> allVouchers = new ArrayList<>();

        List<Voucher> fixedAmountVouchers = findAllByVoucherType(VoucherOption.FIXED_AMOUNT_VOUCHER);
        List<Voucher> percentDiscountVouchers = findAllByVoucherType(VoucherOption.PERCENT_DISCOUNT_VOUCHER);

        allVouchers.addAll(fixedAmountVouchers);
        allVouchers.addAll(percentDiscountVouchers);

        return allVouchers;
    }

    @Override
    public List<Voucher> findAllByVoucherType(VoucherOption voucherOption) {
        switch (voucherOption) {
            case FIXED_AMOUNT_VOUCHER: {
                Map<UUID, Voucher> fixedAmountVouchers = readVouchersFromFile(FIXED_AMOUNT_VOUCHER_FILE_PATH);

                return fixedAmountVouchers.values()
                        .stream()
                        .collect(Collectors.toList());
            }
            case PERCENT_DISCOUNT_VOUCHER: {
                Map<UUID, Voucher> percentDiscountVouchers = readVouchersFromFile(PERCENT_DISCOUNT_VOUCHER_FILE_PATH);

                return percentDiscountVouchers.values()
                        .stream()
                        .collect(Collectors.toList());
            }
            default: {
                return Collections.emptyList();
            }
        }
    }

    private Map<UUID, Voucher> readVouchersFromFile(String voucherFilePath) {
        Map<UUID, Voucher> vouchers = null;

        try {
            FileInputStream fileIn = new FileInputStream(voucherFilePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            vouchers = (Map<UUID, Voucher>) in.readObject();

            in.close();
            fileIn.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        if (vouchers == null) {
            return new HashMap<>();
        }

        return vouchers;
    }

    private void writeVouchersFromFile(Map<UUID, Voucher> vouchers, String voucherFilePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(voucherFilePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(vouchers);

            out.close();
            fileOut.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
