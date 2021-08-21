package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class MemoryVoucherRepository implements VoucherRepository, InitializingBean {

    @Value("${data.storage.name}")
    private String repositoryName;

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public Voucher save(Voucher voucher) {
        storage.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.getOrDefault(voucherId, null));
    }

    @Override
    public List<Voucher> findAll() {
        System.out.println(MessageFormat.format("[Profile local is set.] repositoryName is -> {0}", repositoryName));
        return new ArrayList<>(storage.values());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(MessageFormat.format("[Profile local is set.] repositoryName is -> {0}", repositoryName));
    }
}