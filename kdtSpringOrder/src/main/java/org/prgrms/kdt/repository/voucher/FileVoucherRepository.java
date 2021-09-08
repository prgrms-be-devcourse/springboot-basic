package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.enums.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.prgrms.kdt.utils.FileUtils.getReadAllLines;
import static org.prgrms.kdt.utils.FileUtils.isExistFile;

@Repository
@Profile({"prod", "default"})
public class FileVoucherRepository implements VoucherRepository, InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    @Value("${data.storage.name}")
    private String repositoryName;

    private static final Path voucherFilePath = Paths.get(System.getProperty("user.dir") , "voucher", "voucher.csv");
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> save(Voucher voucher) {
        logger.info("Started Save(), id : {}, type : {}, discount : {}", voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount());
        saveStorage(voucher);
        logger.info("Finished Save()");

        return Optional.ofNullable(storage.put(voucher.getVoucherId(), voucher));
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        logger.info("Started Save(), {}", voucherId.toString());

        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public List<Voucher> findByVoucherId(UUID customerId) {
        return null;
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("Started findAll()");

        loadStorage();
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteVoucher(UUID customerId, UUID voucherId) {
    }

    private void loadStorage() {
        logger.info("Started loadStorage()");

        if(!isExistFile(voucherFilePath)) {
            logger.warn("voucherFilePath does not exist.");
            return;
        }

        try {
            List<String> lines = getReadAllLines(voucherFilePath);
            for (String line : lines) {
                List<String> voucherInfoList = Arrays.asList(line.split(","));
                if (VoucherType.FIXED.toString().equals(voucherInfoList.get(2))) {
                    storage.put(UUID.fromString(voucherInfoList.get(1)), new FixedAmountVoucher(UUID.fromString(voucherInfoList.get(0)), UUID.fromString(voucherInfoList.get(1)), Integer.parseInt(voucherInfoList.get(3)), VoucherType.FIXED));
                } else {
                    storage.put(UUID.fromString(voucherInfoList.get(1)), new PercentDiscountVoucher(UUID.fromString(voucherInfoList.get(0)), UUID.fromString(voucherInfoList.get(1)), Integer.parseInt(voucherInfoList.get(3)), VoucherType.DISCOUNT));
                }
            }
        } catch (NumberFormatException e) {
            logger.error("정수형 변환에 실패했습니다. data : " + e);
            e.printStackTrace();
        }
    }

    private void saveStorage(Voucher voucher) {
        logger.info("Started saveStorage()");

        if(Files.exists(voucherFilePath)) {
            return;
        }

        try {
            logger.info("createVoucherFilePath");
            Files.createDirectory(voucherFilePath.getParent());
            Files.createFile(voucherFilePath.toAbsolutePath());
        } catch (IOException e) {
            logger.error("IOException : " + e);
            e.printStackTrace();
        }

        try(FileWriter writer = new FileWriter(voucherFilePath.toFile(),true)){
            logger.info("Save Voucher Info , id : {}, type : {}, discount : {}", voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount());
            String voucherInfo = String.format("%s,%s,%s,%d%n", voucher.getCustomerId(), voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount(), "\n");
            writer.write(voucherInfo);
            writer.flush();
        }catch (IOException e) {
            logger.error("IOException : " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("[Profile prod is set.] repositoryName is {}", repositoryName);
    }
}