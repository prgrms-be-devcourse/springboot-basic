package org.programmers.weekly.mission.domain.voucher.repository;

import org.programmers.weekly.mission.domain.voucher.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository, InitializingBean ,DisposableBean {
    static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private final String voucherFilePath;

    public FileVoucherRepository(@Value("${file.voucher}") String voucherFilePath) {
        // 함수 호출 (빈 생성주기 관련)
        this.voucherFilePath = voucherFilePath;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public List<Voucher> getVoucherList() {
        return storage.values().stream().toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public void afterPropertiesSet() {
        try {
            FileInputStream fileInputStream = new FileInputStream(voucherFilePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            List<Voucher> vouchers;
            while ((vouchers = (List<Voucher>) objectInputStream.readObject()) != null) {
                vouchers.forEach(voucher -> storage.put(voucher.getVoucherId(), voucher));
            }
        } catch (EOFException e) {
            logger.info("End to read a voucher file.");
        } catch (Exception exception) {
            logger.error("Got error while reading voucher file", exception);
        }
    }


    @Override
    public void destroy() throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(voucherFilePath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(new ArrayList<>(storage.values()));
    }
}
