package org.programmers.voucher.repository;

import org.programmers.voucher.model.FixedDiscountVoucherInterface;
import org.programmers.voucher.model.PercentDiscountVoucherInterface;
import org.programmers.voucher.model.VoucherInterface;
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
public class FileVoucherRepository implements FileMemoryVoucherRepository {
    private final Map<UUID, VoucherInterface> voucherMap = new ConcurrentHashMap<>();

    private final String filePath = "C:\\Users\\skyey\\Desktop\\";
    private final String fileName = "voucher_list.csv";
    File file = new File(filePath + fileName);

    @Override
    public Optional<VoucherInterface> findById(UUID voucherId) {
        return Optional.of(voucherMap.get(voucherId));
    }

    @Override
    public List<VoucherInterface> findAllVouchers() {
        return new ArrayList<>(voucherMap.values());
    }

    @Override
    public void save(VoucherInterface voucher) {
        voucherMap.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public void deleteAll() {
        voucherMap.clear();
    }

    @PostConstruct
    public void loadVoucherList() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            br.lines().map(loadLine -> loadLine.split(","))
                    .forEach(loadLineArr -> {
                        UUID voucherId = UUID.fromString(loadLineArr[0]);
                        VoucherType voucherType = VoucherType.getInputType(loadLineArr[1]);
                        long voucherValue = Long.parseLong(loadLineArr[2]);

                        switch (voucherType) {
                            case FIXED -> save(new FixedDiscountVoucherInterface(voucherId, voucherValue));
                            case PERCENT -> save(new PercentDiscountVoucherInterface(voucherId, voucherValue));
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void saveVoucherList() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (VoucherInterface voucher : voucherMap.values()) {
                String inputVoucherLine = voucher.getVoucherId() + "," + voucher.getVoucherType() + "," + voucher.getVoucherValue() + "\n";
                bw.write(inputVoucherLine);
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
