package org.prgms.springbootbasic.util;


import org.prgms.springbootbasic.config.FileVoucherConfig;
import org.prgms.springbootbasic.domain.voucher.FixedAmountVoucher;
import org.prgms.springbootbasic.domain.voucher.PercentDiscountVoucher;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherType;
import org.prgms.springbootbasic.exception.CommandLineIOException;
import org.prgms.springbootbasic.exception.FileIOException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Profile("dev")
public class VoucherFileManipulator implements FileManipulator {

    private final FileVoucherConfig immutableVoucherFile;
    private final HashMap<UUID, Voucher> memoryCache = new HashMap<>();
    private Path voucherFile;

    public VoucherFileManipulator(FileVoucherConfig immutableVoucherFile) {
        this.immutableVoucherFile = immutableVoucherFile;
    }

    @Override
    public void initFile() {
        voucherFile = Paths.get(immutableVoucherFile.fileName());
        if (!Files.exists(voucherFile, LinkOption.NOFOLLOW_LINKS)) {
            try {
                Files.createFile(voucherFile);
            } catch (IOException e) {
                throw new FileIOException("failed to create file : " + voucherFile.getFileName().toString(), e);
            }
        } else {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(voucherFile.getFileName().toFile()))) {
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] voucherArr = line.split(",");

                    switch (VoucherType.findVoucherType(voucherArr[1])) {
                        case FIXED ->
                                memoryCache.putIfAbsent(UUID.fromString(voucherArr[0]), new FixedAmountVoucher(UUID.fromString(voucherArr[0]),
                                        VoucherType.FIXED,
                                        Long.parseLong(voucherArr[2])));
                        case PERCENT ->
                                memoryCache.putIfAbsent(UUID.fromString(voucherArr[0]), new PercentDiscountVoucher(UUID.fromString(voucherArr[0]),
                                        VoucherType.PERCENT,
                                        Long.parseLong(voucherArr[2])));
                        default -> throw new IllegalArgumentException("invalid voucher option");
                    }

                }

            } catch (IOException e) {
                throw new CommandLineIOException("error occurred reading line with buffered reader", e);
            }
        }
    }

    public void writeFile(Map<UUID, Voucher> memoryCache) {

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(voucherFile.getFileName().toString(), false))) {
            for (Voucher voucher : memoryCache.values()) {
                bufferedWriter.write(voucher.getVoucherId() + "," + voucher.getVoucherType() + "," + voucher.getAmount() + "\n");
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileIOException("unable to write through buffered writer", e);

        }
    }

    public Map<UUID, Voucher> getMemoryCache() {
        return this.memoryCache;
    }
}
