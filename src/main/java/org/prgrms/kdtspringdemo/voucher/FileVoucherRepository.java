package org.prgrms.kdtspringdemo.voucher;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
@Primary
@Qualifier("file")
public class FileVoucherRepository implements VoucherRepository, InitializingBean {
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private final String FILE_NAME = "voucher.csv";

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(FILE_NAME), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            String contents = voucher.saveCSV();
            writer.append(contents).append("\n");
            storage.put(voucher.getVoucherId(), voucher);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return voucher;
    }

    @Override
    public Stream<Voucher> findAll() {
        return storage.values().stream();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[FileVoucherRepository]afterPropertiesSet called!");
        try (BufferedReader reader = Files.newBufferedReader(Path.of(FILE_NAME))) {
            reader.lines().forEach(line -> {
                        String[] dataArray = line.split(",");
                        String voucherType = dataArray[0];
                        String uuid = dataArray[1];
                        String data = dataArray[2];

                        if (voucherType.equals("FixedAmountVoucher")) {
                            var voucher = new FixedAmountVoucher(UUID.fromString(uuid), Long.parseLong(data));
                            storage.put(voucher.getVoucherId(), voucher);
                        } else if (voucherType.equals("PercentDiscountVoucher")) {
                            var voucher = new PercentDiscountVoucher(UUID.fromString(uuid), Long.parseLong(data));
                            storage.put(voucher.getVoucherId(), voucher);
                        } else {
                            System.out.println("None VoucherType!!! : " + voucherType);
                        }
                    });
        } catch (IOException e) {
            System.out.println("Doesn't exist file.");
        }
    }
}