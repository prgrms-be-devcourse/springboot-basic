package org.prgrms.orderapp.repository;

import org.prgrms.orderapp.model.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Profile({"prod", "test"})
public class FileVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    String prefix;

    String filename;

    private Map<UUID, Voucher> storage;

    public FileVoucherRepository(@Value("${data.voucher-storage.prefix:data}") String prefix, @Value("${data.voucher-storage.name:storage.tmp}") String filename) {
        this.prefix = prefix;
        this.filename = filename;
    }

    @PostConstruct
    public void loadStorage() {
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
    public Voucher save(Voucher voucher) {
        logger.info("storage size: {}", storage.size());
        logger.info("vouchers: {}", storage.values());
        storage.put(voucher.getVoucherId(), voucher);
        logger.info("storage size: {}", storage.size());
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values().stream().toList();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
