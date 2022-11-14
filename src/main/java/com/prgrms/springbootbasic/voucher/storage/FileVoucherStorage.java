package com.prgrms.springbootbasic.voucher.storage;

import static com.prgrms.springbootbasic.common.exception.ExceptionMessage.FATAL_FILE_IO_EXCEPTION_MESSAGE;
import static com.prgrms.springbootbasic.common.exception.ExceptionMessage.FILE_NOT_EXIST_EXCEPTION_MESSAGE;

import com.prgrms.springbootbasic.common.exception.FileIOException;
import com.prgrms.springbootbasic.common.exception.FileNotExistException;
import com.prgrms.springbootbasic.common.exception.VoucherTypeNotSupportedException;
import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.prgrms.springbootbasic.voucher.domain.PercentVoucher;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Profile("prod")
@Repository
public class FileVoucherStorage implements VoucherStorage {

    @Value("${classpath.voucher-storage}")
    private String CLASSPATH_VOUCHER_STORAGE;

    private static final String DELIMITER = " ";
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
            File file = openFile();
            write(voucher, file);
        } catch (IOException e) {
            String errorMessage = FATAL_FILE_IO_EXCEPTION_MESSAGE + e.getMessage();
            logger.error(errorMessage);
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
            logger.error(errorMessage);
            throw new FileIOException(errorMessage);
        }
    }

    private File openFile() {
        Resource resource = resourceLoader.getResource(CLASSPATH_VOUCHER_STORAGE);
        try {
            return resource.getFile();
        } catch (FileNotFoundException e) {
            String errorMessage = FILE_NOT_EXIST_EXCEPTION_MESSAGE + e.getMessage();
            logger.error(errorMessage);
            throw new FileNotExistException(errorMessage);
        } catch (IOException e) {
            String errorMessage = FATAL_FILE_IO_EXCEPTION_MESSAGE + e.getMessage();
            logger.error(errorMessage);
            throw new FileIOException(errorMessage);
        }
    }

    private void write(Voucher voucher, File file) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(voucher.getUUID().toString() + " ");
            fileWriter.write(voucher.getClass().getSimpleName() + " ");
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
        String[] columns = voucherStr.split(DELIMITER);

        UUID uuid = UUID.fromString(columns[UUID_COLUMN_INDEX].trim());
        String voucherTypeString = columns[VOUCHER_TYPE_COLUMN_INDEX].trim();
        int discountAmount = Integer.parseInt(columns[DISCOUNT_AMOUNT_INDEX].trim());

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
}
