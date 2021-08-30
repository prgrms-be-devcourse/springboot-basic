package org.programmers.kdt.voucher.repository;

import org.programmers.kdt.voucher.FixedAmountVoucher;
import org.programmers.kdt.voucher.PercentDiscountVoucher;
import org.programmers.kdt.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> cache = new ConcurrentHashMap<>();
    // TOOD: 적절한 데이터 저장 경로 설정 필요
    private final String path = "";
    private final String filename = "VoucherData.txt";

    public FileVoucherRepository() {
        try {
            FileReader fileReader = new FileReader(path + filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = "";
            while (null != (line = bufferedReader.readLine())) {
                String[] data = line.split(" ");
                UUID uuid = UUID.fromString(data[0]);
                String className = data[1];
                Long discount = Long.parseLong(data[2]);

                if ("FixedAmountVoucher".equals(className)) {
                    cache.put(uuid, new FixedAmountVoucher(uuid, discount));
                } else if ("PercentDiscountVoucher".equals(className)) {
                    cache.put(uuid, new PercentDiscountVoucher(uuid, discount));
                }
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e. printStackTrace();
        }
    }

    @Override
    public Voucher save(Voucher voucher) {
        // save to cache
        cache.put(voucher.getVoucherId(), voucher);

        // save to file
        try {
            FileWriter fileWriter = new FileWriter(path + filename, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            UUID uuid = voucher.getVoucherId();
            String[] voucherClassInfo = voucher.getClass().toString().split("\\.");
            String voucherType = voucherClassInfo[voucherClassInfo.length - 1];
            Long discount = voucher.getDiscount();

            bufferedWriter.append(uuid + " " + voucherType + " " + discount);
            bufferedWriter.newLine();

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(cache.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        return new ArrayList<>(cache.values());
    }
}
