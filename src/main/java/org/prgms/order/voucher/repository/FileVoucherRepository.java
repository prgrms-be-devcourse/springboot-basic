package org.prgms.order.voucher.repository;

import org.prgms.order.voucher.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.*;

@Repository
@Profile({"default"})
@PropertySource("classpath:application.yaml")
public class FileVoucherRepository implements VoucherRepository {
    private static final File file = new File(".");


//    @Value("${app.file.voucher}")
    private static String filename = "voucher.txt";

    private String filePath;

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        filePath = MessageFormat.format("{0}/{1}", file.getAbsolutePath(), filename);
        logger.info("findById path : {}",filePath);
        try {
            List<String> strings = Files.readAllLines(Path.of(filePath));
            for (String item : strings) {
                StringTokenizer st = new StringTokenizer(item);
                UUID findVoucherId = UUID.fromString(st.nextToken());

                if (isSame(findVoucherId, voucherId)) {
                    String type = st.nextToken();
                    Long amount = Long.valueOf(st.nextToken());
                    return Optional.of(new VoucherCreateStretage().createVoucher(VoucherIndexType.valueOf(type), new VoucherData(voucherId, amount)));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            logger.info(MessageFormat.format("file read Exception{0}", e.getMessage()));
        }
        return Optional.empty();
    }

    @Override
    public List<Voucher> findByType(VoucherIndexType Type) {
        return null;
    }


    @Override
    public List<Voucher> findByTypeAmount(VoucherIndexType Type, long amount) {
        return null;
    }

    @Override
    public List<Voucher> findAvailable() {
        return null;
    }

    private boolean isSame(UUID voucherId, UUID findVoucherId) {
        return findVoucherId.equals(voucherId);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        filePath = MessageFormat.format("{0}/{1}", file.getAbsolutePath(), filename);
        logger.info("insert path : {}",filePath);
        try {
            Files.writeString(Path.of(filePath),
                    MessageFormat.format("{0} {1} {2}\n",
                            voucher.getVoucherId(),
                            voucher.getType(),
                            Long.toString(voucher.getAmount())), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return voucher;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Voucher> findAll() {
        filePath = MessageFormat.format("{0}/{1}", file.getAbsolutePath(), filename);
        logger.info("findAllVoucher path : {}",filePath);
        List<Voucher> vouchers = new ArrayList<>();
        try {
            List<String> strings = Files.readAllLines(Path.of(filePath));
            strings.forEach((item)->{
                StringTokenizer st = new StringTokenizer(item);
                UUID voucherId = UUID.fromString(st.nextToken());
                String type = st.nextToken();
                Long amount = Long.valueOf(st.nextToken());
                vouchers.add(new VoucherCreateStretage().createVoucher(VoucherIndexType.valueOf(type), new VoucherData(voucherId, amount)));
            });
        } catch (IOException e) {
            e.printStackTrace();
            logger.info(MessageFormat.format("file read Exception{0}", e.getMessage()));
        }
        return vouchers;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void deleteById(UUID voucherId) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public String getVoucherInfoById(UUID voucherId) {
        Voucher voucher= findById(voucherId).get();
        return MessageFormat.format("{0}, VoucherId = {1}, Discount = {2}", voucher.getType(), voucher.getVoucherId(), voucher.getAmount());
    }
}
