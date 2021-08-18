package org.prgrms.orderapp.repository;

import org.prgrms.orderapp.model.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository {
    private static final String FILENAME = MessageFormat.format("{0}/storage.tmp", System.getProperty("user.dir"));
    private Map<UUID, Voucher> storage;

    @PostConstruct
    public void loadStorage() {
        try {
            var file = new File(FILENAME);
            if (file.createNewFile()) {
                storage = new ConcurrentHashMap<>();
            } else {
                var fis = new FileInputStream(file);
                var ois = new ObjectInputStream(fis);

                storage = (ConcurrentHashMap<UUID, Voucher>) ois.readObject();

                ois.close();
                fis.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void saveStorage() {
        try {
            var fos = new FileOutputStream(FILENAME);
            var oos = new ObjectOutputStream(fos);

            oos.writeObject(storage);

            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public void save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }
}
