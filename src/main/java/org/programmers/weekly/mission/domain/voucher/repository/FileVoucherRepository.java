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
public class FileVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {
    static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private final String voucherFilePath;

    public FileVoucherRepository(@Value("${file.voucher}") String voucherFilePath) {
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
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(voucherFilePath));
            List<Voucher> vouchers = new ArrayList<>();

            while ((vouchers = (List<Voucher>) objectInputStream.readObject()) != null) {
                vouchers.forEach(voucher -> storage.put(voucher.getVoucherId(), voucher));
            }

            objectInputStream.close();
        } catch (Exception exception) {
            logger.error("Got error while reading voucher file", exception);
        }
    }

    @Override
    public void destroy() throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(voucherFilePath));
        objectOutputStream.writeObject(new ArrayList<>(storage.values()));
        objectOutputStream.close();
    }
}
