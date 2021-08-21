package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.enums.VoucherType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.prgrms.kdt.utils.FileUtils.getReadAllLines;
import static org.prgrms.kdt.utils.FileUtils.isExistFile;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository, InitializingBean {

    @Value("${data.storage.name}")
    private String repositoryName;

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
        System.out.println(MessageFormat.format("[Profile prod is set.] repositoryName is -> {0}", repositoryName));
        loadStorage();
        return new ArrayList<>(storage.values());
    }

    private void loadStorage() {
        if(!isExistFile(voucherFilePath)) {
            return;
        }

        try {
            List<String> lines = getReadAllLines(voucherFilePath);
            for (String line : lines) {
                List<String> voucherInfo = Arrays.asList(line.split(","));
                if (VoucherType.FIXED.toString().equals(voucherInfo.get(1))) {
                    storage.put(UUID.fromString(voucherInfo.get(0)), new FixedAmountVoucher(UUID.fromString(voucherInfo.get(0)), Integer.parseInt(voucherInfo.get(2))));
                } else {
                    storage.put(UUID.fromString(voucherInfo.get(0)), new PercentDiscountVoucher(UUID.fromString(voucherInfo.get(0)), Integer.parseInt(voucherInfo.get(2))));
                }
            }
        } catch (NumberFormatException e) {
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

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(MessageFormat.format("[Profile prod is set.] repositoryName is -> {0}", repositoryName));
    }
}