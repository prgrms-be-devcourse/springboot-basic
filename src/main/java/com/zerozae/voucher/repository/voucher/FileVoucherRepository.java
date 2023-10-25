package com.zerozae.voucher.repository.voucher;

import com.zerozae.voucher.domain.voucher.FixedDiscountVoucher;
import com.zerozae.voucher.domain.voucher.PercentDiscountVoucher;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.Voucher;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.util.FileUtil;
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


@Profile("file")
@Repository
public class FileVoucherRepository implements VoucherRepository {

    private static final String FILE_PATH = System.getProperty("user.home") + "/voucher.csv";
    private static final String DELIMITER = ",";
    private final Map<UUID, Voucher> vouchers;
    private final FileUtil fileUtil;

    public FileVoucherRepository(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
        this.vouchers = initData();

    }

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getVoucherId(), voucher);
        String voucherInfo = getVoucherInfo(voucher) + System.lineSeparator();
        fileUtil.saveToCsvFile(voucherInfo, FILE_PATH);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values()
                .stream()
                .toList();
    }

    private String getVoucherInfo(Voucher voucher) {
        String voucherId = String.valueOf(voucher.getVoucherId());
        String discount = String.valueOf(voucher.getDiscount());
        String voucherType = String.valueOf(voucher.getVoucherType());
        String useStatusType = String.valueOf(voucher.getUseStatusType());

        return String.join(DELIMITER, voucherId, discount, voucherType, useStatusType);
    }

    private Map<UUID, Voucher> initData() {
        fileUtil.createFile(FILE_PATH);
        Map<UUID, Voucher> loadedVouchers = new ConcurrentHashMap<>();
        List<String> loadedData = fileUtil.loadFromCsvFile(FILE_PATH);

        for (String data : loadedData) {
            String[] voucherInfo = data.split(DELIMITER);
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
        return loadedVouchers;
    }
}
