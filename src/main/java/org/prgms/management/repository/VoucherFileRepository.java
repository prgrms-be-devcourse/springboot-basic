package org.prgms.management.repository;

import org.prgms.management.entity.FixedAmountVoucher;
import org.prgms.management.entity.PercentAmountVoucher;
import org.prgms.management.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import java.io.*;
import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local-file", "default"})
public class VoucherFileRepository implements VoucherRepository {
    @Value("${filedb.voucher}")
    private Resource resource;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean save(Voucher voucher) {
        try {
            String voucherStr = MessageFormat.format("{0},{1},{2},{3}\r\n",
                    voucher.getVoucherId(), voucher.getVoucherType(),
                    voucher.getVoucherName(), voucher.getDiscountNum());

            FileOutputStream fileOutputStream = new FileOutputStream(resource.getFile());

            fileOutputStream.write(voucherStr.getBytes());
            fileOutputStream.close();
            fileOutputStream.flush();

            return true;
        } catch (Throwable e) {
            logger.error("{} can't save voucher file", e.getMessage());
        }
        return false;
    }

    @Override
    public Map<UUID, Voucher> getAll() {
        try {
            InputStream inputStream = resource.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Map<UUID, Voucher> map = new ConcurrentHashMap<>();

            bufferedReader.lines().forEach(
                    line -> {
                        String[] str = line.split(",");
                        UUID uuid = UUID.fromString(str[0]);
                        String type = str[1];
                        String name = str[2];
                        int discountNum = Integer.parseInt(str[3]);
                        Voucher voucher = type.equals("FixedAmountVoucher") ?
                                FixedAmountVoucher.getFixedAmountVoucher(
                                        uuid, discountNum, name, type) :
                                new PercentAmountVoucher(uuid, discountNum, name, type);
                        map.put(uuid, voucher);
                    }
            );

            return map;
        } catch (Throwable e) {
            logger.error("{} can't read voucher file", e.getMessage());
            return null;
        }
    }
}