package org.prgrms.kdt.repository.voucher;


import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.repository.customer.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Repository
@Profile({"dev", "prod"})
public class FileVoucherRepository implements FileRepository, VoucherRepository {

    @Value("${kdt.file.voucher-storage.path}")
    private String path;
    private Map<UUID, Voucher> storage;

    @Override
    @PostConstruct
    public void loadFile() throws ClassNotFoundException, IOException {
        var file = new File(path);
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

    @Override
    public void writeFile() throws IOException {
        try (var fos = new FileOutputStream(path);
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
    public List<Voucher> findAllVoucher() {
        return new ArrayList<>(storage.values());
    }

    // TODO
    @Override
    public Voucher updateType(Voucher customer) {
        throw new UnsupportedOperationException();
    }

    // TODO
    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

}
