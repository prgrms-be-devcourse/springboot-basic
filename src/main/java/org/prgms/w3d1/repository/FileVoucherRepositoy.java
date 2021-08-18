package org.prgms.w3d1.repository;

import org.prgms.w3d1.model.order.Order;
import org.prgms.w3d1.model.voucher.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
public class FileVoucherRepositoy implements VoucherRepository {
    private Map<UUID, Voucher> storage = new ConcurrentHashMap<>();
    private final String PATH = "src/main/resources/";


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public void save(Voucher voucher){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(PATH + "voucher.acc");
            oos = new ObjectOutputStream(fos);
            storage.put(voucher.getVoucherId(), voucher);
            oos.writeObject(storage);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Voucher> findAll() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(PATH + "voucher.acc");
            ois = new ObjectInputStream(fis);
            try {
                storage = (ConcurrentHashMap<UUID, Voucher>) ois.readObject();
                return new ArrayList<>(storage.values());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
