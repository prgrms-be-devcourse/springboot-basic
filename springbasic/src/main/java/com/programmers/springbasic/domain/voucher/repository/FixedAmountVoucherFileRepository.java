package com.programmers.springbasic.domain.voucher.repository;

import com.programmers.springbasic.domain.voucher.entity.FixedAmountVoucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FixedAmountVoucherFileRepository implements VoucherRepository<FixedAmountVoucher, UUID> {
    private static final String FIXED_AMOUNT_VOUCHER_FILE_PATH = "./vouchers/fixedAmountVouchers.csv";

    @Override
    public void save(FixedAmountVoucher voucher) {
        Map<UUID, FixedAmountVoucher> fixedAmountVouchers = readVouchersFromFile();

        fixedAmountVouchers.put(voucher.getCode(), voucher);
        writeVouchersFromFile(fixedAmountVouchers);
    }

    @Override
    public List<FixedAmountVoucher> findAll() {
        Map<UUID, FixedAmountVoucher> fixedAmountVouchers = readVouchersFromFile();

        return fixedAmountVouchers.values()
                .stream()
                .collect(Collectors.toList());
    }

    private Map<UUID, FixedAmountVoucher> readVouchersFromFile() {
        Map<UUID, FixedAmountVoucher> fixedAmountVouchers = null;

        try {
            FileInputStream fileIn = new FileInputStream(FIXED_AMOUNT_VOUCHER_FILE_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            fixedAmountVouchers = (Map<UUID, FixedAmountVoucher>) in.readObject();

            in.close();
            fileIn.close();
        } catch (Exception e) {
        }

        if (fixedAmountVouchers != null) {
            return fixedAmountVouchers;
        } else {
            return new HashMap<>();   // 비어 있다면 비어 있는 Map을 반환
        }
    }

    private void writeVouchersFromFile(Map<UUID, FixedAmountVoucher> fixedAmountVouchers) {
        try {
            FileOutputStream fileOut = new FileOutputStream(FIXED_AMOUNT_VOUCHER_FILE_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(fixedAmountVouchers);

            out.close();
            fileOut.close();
        } catch (Exception e) {
        }
    }
}
