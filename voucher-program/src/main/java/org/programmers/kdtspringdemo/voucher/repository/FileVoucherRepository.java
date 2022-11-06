package org.programmers.kdtspringdemo.voucher.repository;

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

    private static final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

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
    public void afterPropertiesSet() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("voucher.ser"));

            ArrayList<Voucher> voucherList;
            while ((voucherList = (ArrayList<Voucher>) objectInputStream.readObject()) != null) {
                voucherList.forEach((voucher -> storage.put(voucher.getVoucherId(), voucher)));
            }

            objectInputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void destroy() throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("voucher.ser"));
        objectOutputStream.writeObject(new ArrayList<>(storage.values()));
        objectOutputStream.close();
    }
}
