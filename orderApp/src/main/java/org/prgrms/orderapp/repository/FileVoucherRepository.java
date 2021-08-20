package org.prgrms.orderapp.repository;

import org.prgrms.orderapp.model.Voucher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.prgrms.orderapp.io.IOUtils.loadByteFile;
import static org.prgrms.orderapp.io.IOUtils.saveObject;

@Repository
@Primary
@Profile("prod")
public class FileVoucherRepository implements VoucherRepository {

    @Value("${data.voucher-storage.prefix}")
    private String prefix;

    @Value("${data.voucher-storage.name}")
    private String filename;

    private Map<UUID, Voucher> storage;

    @PostConstruct
    public void loadStorage() {
        System.out.println("Profile prod is set. FileVoucherRepository is created.");
        String path = MessageFormat.format("{0}/{1}/{2}", System.getProperty("user.dir"), prefix, filename);
        storage = loadByteFile(path).map(o -> (ConcurrentHashMap<UUID, Voucher>) o).orElseGet(ConcurrentHashMap::new);
    }

    @PreDestroy
    public void saveStorage() {
        String path = MessageFormat.format("{0}/{1}/{2}", System.getProperty("user.dir"), prefix, filename);
        saveObject(storage, path);
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
