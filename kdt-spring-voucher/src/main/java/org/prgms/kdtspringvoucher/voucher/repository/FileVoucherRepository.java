package org.prgms.kdtspringvoucher.voucher.repository;

import org.prgms.kdtspringvoucher.voucher.domain.FixedAmountVoucher;
import org.prgms.kdtspringvoucher.voucher.domain.PercentDiscountVoucher;
import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    private final ApplicationContext applicationContext;

    private Map<UUID, Voucher> store = new ConcurrentHashMap<>();

    @Value("${voucher.file-name}")
    private String FILENAME;

    @Value("${voucher.path}")
    private  String PATH;

    private FileVoucherRepository(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH, true));
            bufferedWriter.write(voucher.getClass().getSimpleName() + "," +
                    voucher.getVoucherID() + "," +
                    voucher.getAmount() + "\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error("Fail save Voucher Data => {}", voucher);
            e.printStackTrace();
        }
        store.put(voucher.getVoucherID(), voucher);
        logger.info("Succeed save Voucher Data => {}", voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(store.get(voucherId));
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("Find All Saved vouchers");
        try {
            Resource resource = applicationContext.getResource(FILENAME);
            Files.readAllLines(resource.getFile().toPath()).forEach(this::getDataByVoucherFile);
        } catch (IOException e) {
            logger.error("Don't have the {}", FILENAME);
            e.printStackTrace();
        }
        return store.keySet()
                .stream()
                .map(key -> store.get(key))
                .toList();
    }

    private void getDataByVoucherFile(String readLine) {
        String[] splitReadLine = readLine.split(",");
        String voucherType = splitReadLine[0];
        String voucherId = splitReadLine[1];
        String amount = splitReadLine[2];
        if (voucherType.equals("FixedAmountVoucher")) {
            store.put(UUID.fromString(voucherId), new FixedAmountVoucher(UUID.fromString(voucherId), Long.parseLong(amount)));
        } else {
            store.put(UUID.fromString(voucherId), new PercentDiscountVoucher(UUID.fromString(voucherId), Long.parseLong(amount)));
        }
    }
}
