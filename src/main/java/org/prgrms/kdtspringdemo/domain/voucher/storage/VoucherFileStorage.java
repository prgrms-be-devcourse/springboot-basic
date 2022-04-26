package org.prgrms.kdtspringdemo.domain.voucher.storage;

import org.prgrms.kdtspringdemo.domain.voucher.data.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class VoucherFileStorage implements VoucherStorage {
    private static final Logger logger = LoggerFactory.getLogger(VoucherFileStorage.class);
    private final ResourceLoader resourceLoader;
    private Map<UUID, Voucher> storage;

    public VoucherFileStorage(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.storage = Collections.emptyMap();
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
    public Map<UUID, Voucher> getStorage() {
        return storage;
    }

}


