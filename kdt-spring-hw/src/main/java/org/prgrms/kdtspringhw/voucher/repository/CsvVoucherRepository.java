package org.prgrms.kdtspringhw.voucher.repository;


import org.prgrms.kdtspringhw.voucher.voucherObj.FixedAmountVoucher;
import org.prgrms.kdtspringhw.voucher.voucherObj.PercentDiscountVoucher;
import org.prgrms.kdtspringhw.voucher.voucherObj.Voucher;
import org.prgrms.kdtspringhw.voucher.voucherObj.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class CsvVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }
    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }
    @Override
    public Map<UUID, Voucher> returnAll() {
        return storage;
    }
    public void roadCSV() {
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(Paths.get("/Users/minkyujeon/Desktop/PJ/programers/spring/w3-SpringBoot_Part_A/kdt-spring-hw/repository/voucher.csv"));
            String line = "";
            while ((line = br.readLine()) != null) {
                Voucher voc;
                String[] token = line.split(",", -1);
                String voucherType = token[0];
                String voucherUUID = token[1];
                if (VoucherType.getVoucherType(voucherType).equals(VoucherType.FIXED_AMOUNT_VOUCHER)) {
                    String voucherAmount = token[2];
                    voc = new FixedAmountVoucher(UUID.fromString(voucherUUID), Long.parseLong(voucherAmount));
                    storage.put(voc.getVoucherId(), voc);
                } else if (VoucherType.getVoucherType(voucherType).equals(VoucherType.FIXED_AMOUNT_VOUCHER)) {
                    String voucherPercent = token[2];
                    voc = new PercentDiscountVoucher(UUID.fromString(voucherUUID), Long.parseLong(voucherPercent));
                    storage.put(voc.getVoucherId(), voc);
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCSV() {
        BufferedWriter bw = null;
        try {
            bw = Files.newBufferedWriter(Paths.get("/Users/minkyujeon/Desktop/PJ/programers/spring/w3-SpringBoot_Part_A/kdt-spring-hw/repository/voucher.csv"), Charset.forName("UTF-8"));
            for (UUID uuid : storage.keySet()) {
                Voucher voc;
                voc = storage.get(uuid);
                if (voc.getType().equals(VoucherType.FIXED_AMOUNT_VOUCHER)) {
                    bw.write("fix," + voc.getVoucherId() + "," + ((FixedAmountVoucher) voc).getData());
                    bw.newLine();
                } else if (voc.getType().equals(VoucherType.PERCENT_DISCOUNT_VOUCHER)) {
                    bw.write("per," + voc.getVoucherId() + "," + ((PercentDiscountVoucher) voc).getData());
                    bw.newLine();
                }
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @PostConstruct
    public void postConstruct() {
        roadCSV();
    }
    @PreDestroy
    public void preDestory() {
        writeCSV();
    }
}