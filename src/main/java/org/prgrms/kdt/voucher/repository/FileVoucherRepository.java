package org.prgrms.kdt.voucher.repository;

import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Qualifier("file")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileVoucherRepository implements VoucherRepository {
    private final String path = "D:\\programmers\\w3\\2d\\w3-SpringBoot_Part_A\\src\\main\\resources\\VoucherRepository.txt";
    private final String SPLIT_CODE = " ";
    private final int TYPE_INDEX = 0;
    private final int UUID_INDEX = 1;
    private final int VALUE_INDEX = 2;
    private final File file;
    private final Map<UUID, Voucher> storage;


    public FileVoucherRepository() throws IOException {
        this.storage = init();
        this.file = new File(path);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) throws IOException {
        insertFile(voucher);
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    private void insertFile(Voucher voucher) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.write(voucher.toString());
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    @Override
    public Map<UUID, Voucher> findByAllVoucher() {
        return storage;
    }

    private Map<UUID, Voucher> init() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));

        Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();
        for (String line : lines) {
            List<String> voucherData = List.of(line.split(SPLIT_CODE));
            VoucherType type = VoucherType.findByVoucherType(voucherData.get(TYPE_INDEX));
            UUID uuid = UUID.fromString(voucherData.get(UUID_INDEX));
            vouchers.put(uuid, newInstanceVoucher(type, uuid, voucherData.get(VALUE_INDEX)));
        }
        return vouchers;
    }

    private Voucher newInstanceVoucher(VoucherType type, UUID uuid, String value) {
        if (type == VoucherType.FIXED) {
            return new FixedAmountVoucher(uuid, parseLong(value));
        }
        return new PercentDiscountVoucher(uuid, parseLong(value));
    }

    private long parseLong(String temp) {
        return Long.parseLong(temp);
    }
}