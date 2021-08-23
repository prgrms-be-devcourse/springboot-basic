package org.programmers.voucher.repository;

import org.programmers.voucher.model.FixedAmountVoucher;
import org.programmers.voucher.model.PercentDiscountVoucher;
import org.programmers.voucher.model.Voucher;
import org.programmers.voucher.model.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> voucherMap = new ConcurrentHashMap<>();

    private final String filePath = "C:\\Users\\skyey\\Desktop\\";
    private final String fileName = "voucher_list.csv";
    File file = new File(filePath + fileName);

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.of(voucherMap.get(voucherId));
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return new ArrayList<>(voucherMap.values());
    }

    @Override
    public void save(Voucher voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
    }

    @PostConstruct
    public void loadVoucherList() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String loadLine;

            while ((loadLine = br.readLine()) != null) {
                String[] loadLineArr = loadLine.split(",");
                UUID voucherId = UUID.fromString(loadLineArr[0]);
                VoucherType voucherType = VoucherType.valueOf(loadLineArr[1]);
                long voucherValue = Long.parseLong(loadLineArr[2]);

                switch (voucherType) {
                    case FIXED -> save(new FixedAmountVoucher(voucherId, voucherValue));
                    case PERCENT -> save(new PercentDiscountVoucher(voucherId, voucherValue));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void saveVoucherList() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (Voucher voucher : voucherMap.values()) {
                String inputVoucherLine = voucher.getVoucherId() + "," + voucher.getVoucherType() + "," + voucher.getVoucherValue() + "\n";
                bw.write(inputVoucherLine);
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
