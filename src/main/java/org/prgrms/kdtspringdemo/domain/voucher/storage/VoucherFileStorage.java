package org.prgrms.kdtspringdemo.domain.voucher.storage;

import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class VoucherFileStorage implements VoucherStorage {
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileStorage.class);
    private Map<UUID, Voucher> storage;

    @Autowired
    ResourceLoader resourceLoader;

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
    public Map<UUID, Voucher> getStorage() {
        return storage;
    }

    public void saveStorageToFile() {
        Resource resource = resourceLoader.getResource("classpath:test.txt");
        File file = null;

        try {
            file = resource.getFile();
        } catch (IOException e) {
            logger.error("File save 중에 IOException 발생");
        }

        try (OutputStream outputStream = new FileOutputStream(file)) {
            storage.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .forEach((value) -> {
                        try {
                            StreamUtils.copy(value.toString().getBytes(), outputStream);
                        } catch (IOException e) {
                            logger.error("File save 중에 IOException 발생");
                        }
                    }
           );
        } catch (IOException e) {
            logger.error("File save 중에 IOException 발생");
        }
    }

}


