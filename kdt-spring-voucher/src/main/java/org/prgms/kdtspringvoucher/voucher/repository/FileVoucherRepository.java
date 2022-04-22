package org.prgms.kdtspringvoucher.voucher.repository;

import org.prgms.kdtspringvoucher.voucher.domain.FixedAmountVoucher;
import org.prgms.kdtspringvoucher.voucher.domain.PercentDiscountVoucher;
import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    private final ApplicationContext applicationContext;
    private final String FILENAME;
    private final String PATH;

    public FileVoucherRepository(ApplicationContext applicationContext, @Value("${voucher.file-name}") String fileName, @Value("${voucher.path}") String path) {
        this.applicationContext = applicationContext;
        this.FILENAME = fileName;
        this.PATH = path;
    }

    @Override
    public Voucher save(Voucher voucher) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH, true));
            bufferedWriter.write(voucher.getVoucherType().toString() + "," +
                    voucher.getVoucherId() + "," +
                    voucher.getAmount() + "," +
                    voucher.getCreatedAt().toString() + "\n");
            bufferedWriter.flush();
            logger.info("Succeed save Voucher Data => {}", voucher);
            return findById(voucher.getVoucherId()).get();
        } catch (IOException e) {
            logger.error("Fail save Voucher Data => {}", voucher);
            return null;
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        List<Voucher> vouchers = new ArrayList<>();
        return findVoucherByVoucherIdFromFile(voucherId, vouchers);
    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        logger.info("Find All Saved vouchers");
        return findAllVoucherDataFromFile(vouchers);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void deleteByCustomerId(UUID customerId) {

    }

    private List<Voucher> findAllVoucherDataFromFile(List<Voucher> list) {
        try {
            Resource resource = applicationContext.getResource("file:" + PATH);
            Files.readAllLines(resource.getFile().toPath())
                    .forEach(readLine -> getDataByVoucherFile(list, readLine));
        } catch (IOException e) {
            logger.error("Don't have the {}", FILENAME);
            e.printStackTrace();
        }
        return list;
    }

    private Optional<Voucher> findVoucherByVoucherIdFromFile(UUID voucherId, List<Voucher> findByIdList) {
        try {
            Resource resource = applicationContext.getResource("file:" + PATH);
            Files.readAllLines(resource.getFile().toPath())
                    .stream()
                    .forEach(readLine -> {
                        String[] splitReadLine = readLine.split(",");
                        String readVoucherId = splitReadLine[1];
                        if (UUID.fromString(readVoucherId).equals(voucherId))
                            getDataByVoucherFile(findByIdList, readLine);
                    });
            return Optional.ofNullable(findByIdList.get(0));
        } catch (IOException e) {
            logger.error("Don't have the {}", FILENAME);
            return Optional.empty();
        }
    }

    private void getDataByVoucherFile(List<Voucher> list,String readLine) {
        String[] splitReadLine = readLine.split(",");
        VoucherType voucherType = VoucherType.valueOf(splitReadLine[0]);
        String voucherId = splitReadLine[1];
        String amount = splitReadLine[2];
        LocalDateTime createdAt = LocalDateTime.parse(splitReadLine[3]);

        if (voucherType == VoucherType.FIXED) {
            list.add(new FixedAmountVoucher(UUID.fromString(voucherId), Long.parseLong(amount),voucherType, createdAt));
        }
        else if (voucherType == VoucherType.PERCENT){
            list.add(new PercentDiscountVoucher(UUID.fromString(voucherId), Long.parseLong(amount),voucherType, createdAt));
        }
    }
}