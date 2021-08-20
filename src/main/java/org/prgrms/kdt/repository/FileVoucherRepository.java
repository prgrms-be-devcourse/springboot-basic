package org.prgrms.kdt.repository;


import org.prgrms.kdt.model.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository {

    private static final String FILEPATH = System.getProperty("user.dir") + "/voucher_data.ser";
    private Map<UUID, Voucher> storage;

    @PostConstruct
    public void loadFile() throws IOException, ClassNotFoundException {

        var file = new File(FILEPATH);
        if (file.createNewFile()) {
            storage = new ConcurrentHashMap<>();
            writeFile();
        } else {
            try (var fis = new FileInputStream(file);
                 var bis = new BufferedInputStream(fis);
                 var ois = new ObjectInputStream(bis);) {
                storage = (ConcurrentHashMap<UUID, Voucher>) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeFile() throws IOException {
        try (var fos = new FileOutputStream(FILEPATH);
             var bos = new BufferedOutputStream(fos);
             var oos = new ObjectOutputStream(bos);) {
            oos.writeObject(storage);
            oos.writeObject(null);
        }

    }


    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        try {
            writeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voucher;
    }

    @Override
    public Collection<Voucher> findAllVoucher() {
        return storage.values();
    }
}
