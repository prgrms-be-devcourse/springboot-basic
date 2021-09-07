package org.prgrms.dev.voucher.repository;

import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.VoucherType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
@Qualifier("file")
@Profile({"local", "file"})
public class FileVoucherRepository implements VoucherRepository {
    private static final String PATH = "src/main/resources/voucher.csv";
    private static final String USER_DIR = "user.dir";
    private static final String SPLIT_CODE = ":";
    private static final int TYPE_INDEX = 0;
    private static final int UUID_INDEX = 1;
    private static final int VALUE_INDEX = 2;

    private final File file;
    private final Map<UUID, Voucher> store;


    public FileVoucherRepository() {
        this.file = new File(System.getProperty(USER_DIR), PATH);
        this.store = init();
    }

    private Map<UUID, Voucher> init() {
        Map<UUID, Voucher> vouchers = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] voucherInfo = line.split(SPLIT_CODE);
                UUID voucherId = UUID.fromString(voucherInfo[UUID_INDEX]);
                long value = Long.parseLong(voucherInfo[VALUE_INDEX]);
                Voucher voucher = VoucherType.getVoucherType(voucherInfo[TYPE_INDEX], voucherId, value);
                vouchers.put(voucherId, voucher);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vouchers;
    }

    @Override
    public Voucher create(Voucher voucher) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.write(voucher.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(store.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(store.values());
    }
}
