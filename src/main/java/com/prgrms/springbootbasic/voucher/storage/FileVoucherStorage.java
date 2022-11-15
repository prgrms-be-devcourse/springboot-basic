package com.prgrms.springbootbasic.voucher.storage;

import com.prgrms.springbootbasic.common.exception.FileIOException;
import com.prgrms.springbootbasic.common.exception.FileNotExistException;
import com.prgrms.springbootbasic.common.exception.VoucherTypeNotSupportedException;
import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.prgrms.springbootbasic.voucher.domain.PercentVoucher;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.prgrms.springbootbasic.common.exception.ExceptionMessage.*;

@Profile("prod")
@Repository
public class FileVoucherStorage implements VoucherStorage {

    private static final String DELIMITER = " ";
    private static final int UUID_COLUMN_INDEX = 0;
    private static final int VOUCHER_TYPE_COLUMN_INDEX = 1;
    private static final int DISCOUNT_AMOUNT_INDEX = 2;
    private static final int VOUCHER_COLUMN_SIZE = 3;

    private final ResourceLoader resourceLoader;

    private final String VOUCHER_STORAGE_CLASSPATH;

    public FileVoucherStorage(ResourceLoader resourceLoader, @Value("${classpath.voucher-storage}") String VoucherStorageClasspath) {
        this.resourceLoader = resourceLoader;
        this.VOUCHER_STORAGE_CLASSPATH = VoucherStorageClasspath;
    }

    @Override
    public void save(Voucher voucher) {
        try {
            File file = openFile();
            write(voucher, file);
        } catch (IOException e) {
            String errorMessage = FATAL_FILE_IO_EXCEPTION_MESSAGE + e.getMessage();
            throw new FileIOException(errorMessage);
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            File file = openFile();
            return readAll(file);
        } catch (IOException e) {
            String errorMessage = FATAL_FILE_IO_EXCEPTION_MESSAGE + e.getMessage();
            throw new FileIOException(errorMessage);
        }
    }

    private File openFile() {
        Resource resource = resourceLoader.getResource(VOUCHER_STORAGE_CLASSPATH);
        try {
            return resource.getFile();
        } catch (FileNotFoundException e) {
            String errorMessage = FILE_NOT_EXIST_EXCEPTION_MESSAGE + e.getMessage();
            throw new FileNotExistException(errorMessage);
        } catch (IOException e) {
            String errorMessage = FATAL_FILE_IO_EXCEPTION_MESSAGE + e.getMessage();
            throw new FileIOException(errorMessage);
        }
    }

    private void write(Voucher voucher, File file) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(voucher.getUUID().toString() + DELIMITER);
            fileWriter.write(voucher.getClass().getSimpleName() + DELIMITER);
            fileWriter.write(voucher.getDiscountRate() + System.lineSeparator());
            fileWriter.flush();
        }
    }

    private List<Voucher> readAll(File file) throws IOException {
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
        List<String> columns = List.of(voucherStr.split(DELIMITER));

        validSize(columns);

        UUID uuid = UUID.fromString(columns.get(UUID_COLUMN_INDEX).trim());
        String voucherTypeString = columns.get(VOUCHER_TYPE_COLUMN_INDEX).trim();
        int discountAmount = Integer.parseInt(columns.get(DISCOUNT_AMOUNT_INDEX).trim());

        VoucherType voucherType = VoucherType.fromClassName(voucherTypeString);
        switch (voucherType) {
            case FIXED_AMOUNT -> {
                return new FixedAmountVoucher(uuid, discountAmount);
            }
            case PERCENT -> {
                return new PercentVoucher(uuid, discountAmount);
            }
            default -> throw new VoucherTypeNotSupportedException(voucherTypeString);
        }
    }

    private void validSize(List<String> columns) {
        if (columns.size() != VOUCHER_COLUMN_SIZE) {
            throw new FileIOException(FILE_NUMBER_OF_COLUMN_NOT_MATCHED);
        }
    }
}
