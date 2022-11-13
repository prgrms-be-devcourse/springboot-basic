package com.prgrms.springbootbasic.voucher.storage;

import com.prgrms.springbootbasic.common.exception.VoucherTypeNotSupportedException;
import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.prgrms.springbootbasic.voucher.domain.PercentVoucher;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Profile("default")
@Repository
public class FileVoucherStorage implements VoucherStorage {

    private static final String CLASSPATH_FILE_NAME = "classpath:voucher_storage.txt";
    private static final int UUID_COLUMN_INDEX = 0;
    private static final int VOUCHER_TYPE_COLUMN_INDEX = 1;
    private static final int DISCOUNT_AMOUNT_INDEX = 2;

    private final ResourceLoader resourceLoader;

    private final Logger logger = LoggerFactory.getLogger(FileVoucherStorage.class);

    public FileVoucherStorage(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void save(Voucher voucher) {
        try {
            Resource resource = resourceLoader.getResource(CLASSPATH_FILE_NAME);
            File file = resource.getFile();
            write(voucher, file);
        } catch (FileNotFoundException e) {
            logger.warn("File not found. create new file.");
        } catch (IOException e) {
            logger.error("Fatal error while opening file.");
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            Resource resource = resourceLoader.getResource(CLASSPATH_FILE_NAME);
            File file = resource.getFile();
            return read(file);
        } catch (FileNotFoundException e) {
            logger.warn("File not found. create new file.");
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            logger.error("Fatal error while opening file.");
            throw new RuntimeException(e.getMessage());
        }
    }

    private void write(Voucher voucher, File file) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(voucher.getUUID().toString() + " ");
            fileWriter.write(voucher.getVoucherType() + " ");
            fileWriter.write(voucher.getDiscountRate() + System.lineSeparator());
            fileWriter.flush();
        }
    }

    private List<Voucher> read(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<Voucher> vouchers = new ArrayList<>();
            String voucherStr;
            while ((voucherStr = br.readLine()) != null) {
                vouchers.add(mapToVoucher(voucherStr));
            }
            return vouchers;
        }
    }

    private Voucher mapToVoucher(String voucherStr) {
        String[] columns = voucherStr.split(" ");

        UUID uuid = UUID.fromString(columns[UUID_COLUMN_INDEX]);
        String voucherTypeString = columns[VOUCHER_TYPE_COLUMN_INDEX];
        int discountAmount = Integer.parseInt(columns[DISCOUNT_AMOUNT_INDEX]);

        VoucherType voucherType = VoucherType.from(voucherTypeString);
        switch (voucherType) {
            case FIXED_AMOUNT -> {
                return new FixedAmountVoucher(uuid, voucherTypeString, discountAmount);
            }
            case PERCENT -> {
                return new PercentVoucher(uuid, voucherTypeString, discountAmount);
            }
            default -> throw new VoucherTypeNotSupportedException(voucherTypeString);
        }
    }
}
