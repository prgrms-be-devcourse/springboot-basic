package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.enums.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository{

    private static final Path voucherFilePath = Paths.get(System.getProperty("user.dir") , "voucher", "voucher.csv");
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        saveStorage(voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        loadStorage();
        return new ArrayList<>(storage.values());
    }

    private void loadStorage() {
        if(!Files.exists(voucherFilePath))
            return;

        try {
            List<String> lines = Files.readAllLines(voucherFilePath);
            for (String line : lines) {
                List<String> voucherInfo = Arrays.asList(line.split(","));
                if (VoucherType.FIXED.toString().equals(voucherInfo.get(1))) {
                    storage.put(UUID.fromString(voucherInfo.get(0)), new FixedAmountVoucher(UUID.fromString(voucherInfo.get(0)), Integer.parseInt(voucherInfo.get(2))));
                } else {
                    storage.put(UUID.fromString(voucherInfo.get(0)), new PercentDiscountVoucher(UUID.fromString(voucherInfo.get(0)), Integer.parseInt(voucherInfo.get(2))));
                }
            }
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    private void saveStorage(Voucher voucher) {
        if(!Files.exists(voucherFilePath)) {
            try {
                Files.createDirectory(voucherFilePath.getParent());
                Files.createFile(voucherFilePath.toAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try(FileWriter writer = new FileWriter(voucherFilePath.toFile(),true)){
            String voucherInfo = String.format("%s,%s,%d%n", voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount(), "\n");
            writer.write(voucherInfo);
            writer.flush();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}