package com.prgrm.kdt.voucher.repository;

import com.prgrm.kdt.voucher.domain.FixedAmountVoucher;
import com.prgrm.kdt.voucher.domain.PercentDiscountVoucher;
import com.prgrm.kdt.voucher.domain.Voucher;
import com.prgrm.kdt.voucher.domain.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.prgrm.kdt.voucher.domain.VoucherType.*;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {

    private final static String path = "src/main/resources/VoucherRepository.csv";
    private final File file;
    private final Map<UUID, Voucher> storage;

    public FileVoucherRepository() {
        this.file = new File(path);
        this.storage = readFile();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Map<UUID, Voucher> findAll() {
        return storage;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
//    TODO : 파일에도 바우처 insert해주기
        writeFile(voucher);
        return voucher;
    }

    public Map<UUID, Voucher> readFile() {
        Map<UUID, Voucher> voucherList = new ConcurrentHashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = "";
            while ((line = br.readLine()) != null) {
                String[] token = line.split(" ");
                for (int i = 0; i < token.length; i++) {
                    VoucherType type = VoucherType.findByVoucherType(token[0].toUpperCase(Locale.ROOT));
                    UUID voucherId = UUID.fromString(token[1]);
                    long value = Long.parseLong(token[2]);
                    if (type == FIXED) {
                        Voucher voucher = new FixedAmountVoucher(voucherId, value);
                        voucherList.put(UUID.fromString(token[1]), voucher);
                        continue;
                    }
                    if (type == PERCENT) {
                        Voucher voucher = new PercentDiscountVoucher(voucherId, value);
                        voucherList.put(UUID.fromString(token[1]), voucher);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucherList;
    }

    public void writeFile(Voucher voucher) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(voucher.toString());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
