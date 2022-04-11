package org.prgms.management.repository;

import org.prgms.management.entity.FixedAmountVoucher;
import org.prgms.management.entity.PercentAmountVoucher;
import org.prgms.management.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local-file", "default"})
public class VoucherFileRepository implements VoucherRepository {
    @Value("${filedb.path}")
    private String path;
    @Value("${filedb.voucher}")
    private String name;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private File file;

    @Override
    public boolean save(Voucher voucher) {
        try {
            file = new File(MessageFormat.format("{0}/{1}", path, name));
            file.createNewFile();
            System.out.println(file.getName());
            String voucherStr = MessageFormat.format("{0},{1},{2},{3}\r\n",
                    voucher.getVoucherId(), voucher.getVoucherType(),
                    voucher.getVoucherName(), voucher.getDiscountNum());
            BufferedWriter bufferedWriter =
                    new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(voucherStr);
            bufferedWriter.flush();
            return true;
        } catch (Throwable e) {
            logger.error(MessageFormat.format
                    ("{0} can't save voucher file", e.getMessage()));
        }
        return false;
    }

    @Override
    public Map<UUID, Voucher> getAll() {
        try {
            file = new File(MessageFormat.format("{0}/{1}", path, name));
            file.createNewFile();
            List<String> lines = Files.readAllLines(Path.of(file.getPath()));
            Map<UUID, Voucher> map = new ConcurrentHashMap<>();

            for (String line : lines) {
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
            return map;
        } catch (Throwable e) {
            logger.error(MessageFormat.format
                    ("{0} can't read voucher file", e.getMessage()));
            return null;
        }
    }
}