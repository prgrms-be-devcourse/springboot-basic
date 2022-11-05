package org.programmers.kdtspringdemo.voucher.repository;

import org.programmers.kdtspringdemo.voucher.model.FixedAmountVoucher;
import org.programmers.kdtspringdemo.voucher.model.Voucher;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository, InitializingBean, DisposableBean {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);

        return voucher;
    }

    @Override
    public List<String> getVoucherList() {

        return storage.values().stream().map((Object::toString)).toList();

    }

    @Override
    public void afterPropertiesSet() throws Exception {

        // voucher.ser -> storage

        FileInputStream fileInputStream = new FileInputStream("voucher.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Voucher voucher = (Voucher) objectInputStream.readObject();

        objectInputStream.close();

    }

    @Override
    public void destroy() throws Exception {

        // storage -> voucher.ser

        FileOutputStream fileOutputStream = new FileOutputStream("voucher.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        objectOutputStream.writeObject(voucher);
        objectOutputStream.close();

    }
}
