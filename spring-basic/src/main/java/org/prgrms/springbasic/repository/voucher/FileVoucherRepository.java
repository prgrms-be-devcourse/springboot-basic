package org.prgrms.springbasic.repository.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

import static org.prgrms.springbasic.utils.enumm.FilePath.VOUCHER_FILE_PATH;

@Repository
@Profile("dev")
@Slf4j
public class FileVoucherRepository implements VoucherRepository {

    private static final String path = VOUCHER_FILE_PATH.getPath();
    private final File file = new File(path);
    private final BufferedWriter output = new BufferedWriter(new FileWriter(file));
    private final BufferedReader input = new BufferedReader(new FileReader(file));
    private final Map<UUID, Voucher> storage = new LinkedHashMap<>();
    private final List<Voucher> vouchers = new ArrayList<>();

    FileVoucherRepository() throws IOException {
    }

    @Override
    public void save(Voucher voucher) {
        String template = voucher.toString();
        storage.put(voucher.getVoucherId(), voucher);

        try {
            output.write(template);
            output.flush();
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage());
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        String sLine;

        try {
            while((sLine = input.readLine()) != null) {
                fillVouchers(sLine);
            }
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage());
        }

        return vouchers;
    }

    @Override
    public int countStorageSize() {
        return storage.size();
    }

    @Override
    public void clear() {
        file.delete();
        storage.clear();
        vouchers.clear();
    }

    private void fillVouchers(String fileVoucher) {
        String voucherId = fileVoucher.split("/")[0]; //voucherID, voucherType, discountInfo
        vouchers.add(storage.get(UUID.fromString(voucherId)));
    }
}