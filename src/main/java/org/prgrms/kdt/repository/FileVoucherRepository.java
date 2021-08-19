package org.prgrms.kdt.repository;


import org.prgrms.kdt.domain.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository {

    private static final String FILEPATH = System.getProperty("user.dir") + "/voucher_data.txt";
    private Map<UUID, Voucher> storage;

    @PostConstruct
    public void loadStorage() {
        try {
            var file = new File(FILEPATH);
            if (file.createNewFile()) {
                storage = new ConcurrentHashMap<>();
            }
            else {
                var fis = new FileInputStream(file);
                var bis = new BufferedInputStream(fis);
                var ois = new ObjectInputStream(bis);
                storage = (ConcurrentHashMap<UUID, Voucher>) ois.readObject();
                ois.close();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void writeStorage() {
        try {
            var fos = new FileOutputStream(FILEPATH);
            var bos = new BufferedOutputStream(fos);
            var oos = new ObjectOutputStream(bos);

            oos.writeObject(storage);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
    public Collection<Voucher> findAllVoucher() {
        return storage.values();
    }
}
