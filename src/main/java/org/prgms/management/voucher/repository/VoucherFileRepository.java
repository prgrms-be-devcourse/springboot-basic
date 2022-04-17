package org.prgms.management.voucher.repository;

import org.prgms.management.voucher.entity.Voucher;
import org.prgms.management.voucher.entity.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local-file", "default"})
public class VoucherFileRepository implements VoucherRepository {
    @Value("${filedb.voucher}")
    private String filePath;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Optional<Voucher> insert(Voucher voucher) {
        try (
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new FileWriter(filePath, true)
                );
        ) {
            String voucherStr = MessageFormat.format("{0},{1},{2},{3}\r\n",
                    voucher.getVoucherId(), voucher.getVoucherType(),
                    voucher.getVoucherName(), voucher.getDiscountNum());

            bufferedWriter.write(voucherStr);
            return Optional.of(voucher);
        } catch (IOException e) {
            logger.error("{} can't save voucher file", e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Map<UUID, Voucher> getAll() {
        Map<UUID, Voucher> map = new ConcurrentHashMap<>();

        try (
                BufferedReader bufferedReader = new BufferedReader(
                        new FileReader(filePath));
        ) {
            bufferedReader.lines().forEach(
                    line -> {
                        String[] str = line.split(",");
                        UUID uuid = UUID.fromString(str[0]);
                        String type = str[1];
                        String name = str[2];
                        int discountNum = Integer.parseInt(str[3]);
                        Optional<Voucher> voucher = VoucherType.createVoucher(
                                uuid, discountNum, name, type);
                        voucher.ifPresent(value -> map.put(uuid, value));
                    }
            );
        } catch (IOException e) {
            logger.error("{} can't read voucher file", e.getMessage(), e);
        }

        return map;
    }
}