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

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    private final ApplicationContext applicationContext;

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
        logger.info("Succeed save Voucher Data => {}", voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<Voucher> vouchers = new ArrayList<>();
        findVoucherByVoucherIdFromFile(voucherId, vouchers);
        return Optional.ofNullable(vouchers.get(0));
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        logger.info("Find All Saved vouchers");
        findAllVoucherDataFromFile(vouchers);
        return vouchers;
    }

    private void findAllVoucherDataFromFile(List<Voucher> list) {
        try {
            Resource resource = applicationContext.getResource("file:" + PATH);
            Files.readAllLines(resource.getFile().toPath())
                    .forEach(readLine -> getDataByVoucherFile(list, readLine));
        } catch (IOException e) {
            logger.error("Don't have the {}", FILENAME);
            e.printStackTrace();
        }
    }

    private void findVoucherByVoucherIdFromFile(UUID voucherId, List<Voucher> findByIdList) {
        try {
            Resource resource = applicationContext.getResource("file:" + PATH);
            Files.readAllLines(resource.getFile().toPath())
                    .stream()
                    .filter(readLine -> {
                        String[] splitReadLine = readLine.split(",");
                        String readVoucherId = splitReadLine[1];
                        return UUID.fromString(readVoucherId).equals(voucherId);
                    })
                    .forEach(readLine -> getDataByVoucherFile(findByIdList, readLine));
        } catch (IOException e) {
            logger.error("Don't have the {}", FILENAME);
            e.printStackTrace();
        }
    }

    private void getDataByVoucherFile(List<Voucher> list,String readLine) {
        String[] splitReadLine = readLine.split(",");
        String voucherType = splitReadLine[0];
        String voucherId = splitReadLine[1];
        String amount = splitReadLine[2];

        if (voucherType.equals("FixedAmountVoucher")) {
            list.add(new FixedAmountVoucher(UUID.fromString(voucherId), Long.parseLong(amount)));
        }
        else {
            list.add(new PercentDiscountVoucher(UUID.fromString(voucherId), Long.parseLong(amount)));
        }
    }
}