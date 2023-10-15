package com.zerozae.voucher.repository.voucher;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.exception.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.zerozae.voucher.common.message.MessageConverter.getMessage;

@Profile("prod")
@Repository
public class FileVoucherRepository implements VoucherRepository{
    private static final String FILE_PATH = System.getProperty("user.home") + "/voucher.csv";
    private static final String DELIMITER = ",";
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final Map<UUID, Voucher> vouchers;
    public FileVoucherRepository() {
        createFile();
        this.vouchers = loadVoucherFromCsvFile();

    }
    @Override
    public void save(Voucher voucher) {
        try {
            vouchers.put(voucher.getVoucherId(), voucher);
            String voucherInfo = getVoucherInfo(voucher) + System.lineSeparator();
            Files.writeString(Path.of(FILE_PATH), voucherInfo, StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("Error Message ={}", e.getMessage());
            throw ExceptionHandler.err(getMessage("WRITE_FILE_ERROR.MSG"));
        }
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return vouchers.values().stream().toList();
    }

    public Map<UUID, Voucher> loadVoucherFromCsvFile() {
        Map<UUID, Voucher> loadedVouchers = new ConcurrentHashMap<>();
        try {
            List<String> lines = Files.readAllLines(Path.of(FILE_PATH), StandardCharsets.UTF_8);

            for (String line : lines) {
                String[] voucherInfo = line.split(DELIMITER);
                UUID voucherId = UUID.fromString(voucherInfo[0]);
                long discount = Long.parseLong(voucherInfo[1]);
                VoucherType voucherType = VoucherType.valueOf(voucherInfo[2]);
                UseStatusType useStatusType = UseStatusType.valueOf(voucherInfo[3]);

                Voucher voucher = switch (voucherType) {
                    case FIXED -> new FixedDiscountVoucher(voucherId, discount, useStatusType);
                    case PERCENT -> new PercentDiscountVoucher(voucherId, discount, useStatusType);
                };
                loadedVouchers.put(voucherId, voucher);
            }
        } catch (IOException e) {
            logger.error("Error Message ={}", e.getMessage());
            throw ExceptionHandler.err(getMessage("READ_FILE_ERROR.MSG"));
        }
        return loadedVouchers;
    }
    private String getVoucherInfo(Voucher voucher) {
        String voucherId = String.valueOf(voucher.getVoucherId());
        String discount = String.valueOf(voucher.getDiscount());
        String voucherType = String.valueOf(voucher.getVoucherType());
        String useStatusType = String.valueOf(voucher.getUseStatusType());

        return String.join(DELIMITER, voucherId, discount, voucherType, useStatusType);
    }
    private void createFile(){
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.error("Error Message ={}", e.getMessage());
                throw ExceptionHandler.err(getMessage("CREATE_FILE_ERROR.MSG"));
            }
        }
    }
}
