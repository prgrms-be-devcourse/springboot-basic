package org.prgrms.kdtspringorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

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
    public Map<UUID, Voucher> getAllVoucher() {
        return storage;
    }

    @Override
    public void insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        writeFile(voucher);
    }

    public Map<UUID, Voucher> readFile() {
        Map<UUID, Voucher> voucherList = new ConcurrentHashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line = "";
            while ((line = br.readLine()) != null) {
                String[] token = line.split(" ");

                for (int i = 0; i < token.length; i++) {
                    String type = token[0];
                    UUID voucherId = UUID.fromString(token[1]); //id
                    long value = Long.parseLong(token[2]);
                    if (type.equals("Fixed")) {
                        Voucher voucher = new FixedAmountVoucher(voucherId,value);
                        voucherList.put(UUID.fromString(token[1]), voucher);
                        continue;
                    }
                    if (type.equals("Percent")) {
                        Voucher voucher = new PercentDiscountVoucher(voucherId, value);
                        voucherList.put(UUID.fromString(token[1]), voucher);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
        }
    }
}