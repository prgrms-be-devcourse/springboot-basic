package org.prgrms.weeklymission.voucher.repository;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.weeklymission.voucher.domain.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

import static org.prgrms.weeklymission.utils.Path.VOUCHER_FILE_DB_PATH;

@Repository
@Profile("dev")
@Slf4j
public class FileVoucherRepository implements VoucherRepository {
    private static final String path = VOUCHER_FILE_DB_PATH;
    private final File file = new File(path);
    private final BufferedWriter output = new BufferedWriter(new FileWriter(file));
    private final BufferedReader input = new BufferedReader(new FileReader(file));
    private final Map<String, Voucher> storage = new LinkedHashMap<>();
    private final List<Voucher> vouchers = new ArrayList<>();

    private FileVoucherRepository() throws IOException {
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId.toString()));
    }

    @Override
    public void save(Voucher voucher) {
        String template = voucher.toString() + "\n";
        try {
            storage.put(voucher.getVoucherId().toString(), voucher);
            output.write(template);
            output.flush();
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage());
        }
    }

    @Override
    public List<Voucher> findAll() {
        try {
            String sLine = null;
            while((sLine = input.readLine()) != null) {
                fileReadAndAddVoucher(sLine);
            }
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage());
        }

        return vouchers;
    }

    private void fileReadAndAddVoucher(String fileVoucher) {
        String voucherId = fileVoucher.split(" ")[3];
        vouchers.add(storage.get(voucherId));
    }

    @Override
    public int getStorageSize() {
        return storage.size();
    }

    @Override
    public void clear() {
        file.delete();
        storage.clear();
        vouchers.clear();
    }
}