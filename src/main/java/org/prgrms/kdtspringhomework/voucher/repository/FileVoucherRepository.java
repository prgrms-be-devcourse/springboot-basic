package org.prgrms.kdtspringhomework.voucher.repository;


import org.prgrms.kdtspringhomework.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdtspringhomework.voucher.domain.PercentDiscountVoucher;
import org.prgrms.kdtspringhomework.voucher.domain.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class FileVoucherRepository implements VoucherRepository {
    private static final String USER_DIR = "user.dir";
    private static final String FILE_PATH = "src/main/resources/voucher.csv";
    private static final Path FILE_DIR = Paths.get(System.getProperty(USER_DIR), FILE_PATH);
    private static final String SEPARATION_CRITERIA = ",";
    private static final int TYPE_INDEX = 0;
    private static final int UUID_INDEX = 1;
    private static final int AMOUNT_INDEX = 2;
    private static final String FIXED = "fixed";
    private static final String PERCENT = "percent";
    private final Map<UUID, Voucher> FILE_STORAGE;

    public FileVoucherRepository() {
        FILE_STORAGE = seperateVouchers();
    }

    private Map<UUID, Voucher> seperateVouchers() {
        Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();
        try {
            List<String> voucherList = Files.readAllLines(FILE_DIR);
            for (String line : voucherList) {
                String[] voucherArray = line.split(SEPARATION_CRITERIA);
                String customerType = voucherArray[TYPE_INDEX];
                UUID customerId = UUID.fromString(voucherArray[UUID_INDEX]);
                long customerAmount = Long.parseLong(voucherArray[AMOUNT_INDEX]);

                Voucher voucher = null;
                if (customerType.equals(FIXED)) {
                    voucher = new FixedAmountVoucher(customerId, customerAmount);
                } else if (customerType.equals(PERCENT)) {
                    voucher = new PercentDiscountVoucher(customerId, customerAmount);
                }
                voucherMap.put(customerId, voucher);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucherMap;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(FILE_STORAGE.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(FILE_STORAGE.values());
    }

    @Override
    public Voucher add(Voucher voucher) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_PATH, true));
            bufferedWriter.write(voucher.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
