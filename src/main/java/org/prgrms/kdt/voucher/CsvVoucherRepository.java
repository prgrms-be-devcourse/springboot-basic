package org.prgrms.kdt.voucher;

import com.opencsv.exceptions.CsvValidationException;
import org.prgrms.kdt.engine.OpenCsv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"local"})
public class CsvVoucherRepository implements VoucherRepository {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${local.file_path}")
    private String filePath;
    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    OpenCsv csvWriter = new OpenCsv();

    @PostConstruct
    public void load() {
        Optional<Map<UUID, Voucher>> storage = csvWriter.loadFile(filePath);
        if (storage.isPresent()) {
            for (Voucher voucher: storage.get().values()) {
                insert(voucher);
            }
        }
        logger.info("load the voucher");
    }

    @PreDestroy
    public void save() throws IOException {
        csvWriter.saveFile(storage, filePath);
        logger.info("save the voucher");
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        return storage.put(voucher.getVoucherId(), voucher);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Map<UUID, Voucher> getStorage() {
        return storage;
    }
}
