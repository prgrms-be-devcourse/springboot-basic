package com.programmers.voucher.repository;

import com.programmers.voucher.repository.dumper.Dumper;
import com.programmers.voucher.repository.loader.Loader;
import com.programmers.voucher.voucher.Voucher;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;


@Profile("dev")
@Component
public class FileVoucherRepository implements VoucherRepository {
    public static final String VOUCHER_TYPE = "type";
    public static final String VOUCHER_VALUE = "value";
    private final Wini wini;
    private final Map<UUID, Voucher> cacheMap;
    private final Loader loader;
    private final Dumper dump;

    @Autowired
    public FileVoucherRepository(Wini wini, Loader loader, Dumper dump) {
        this.wini = wini;
        this.loader = loader;
        this.dump = dump;

        this.cacheMap = new HashMap<>();
    }

    @PostConstruct
    public void setting() {
        loader.load(cacheMap);
    }

    @PreDestroy
    void syncToFile() {
        dump.dump(cacheMap);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(cacheMap.get(voucherId));
    }


    @Override
    public List<Voucher> findAllVouchers() {
        List<Voucher> vouchers = new ArrayList<>();

        for (UUID key : cacheMap.keySet()) {
            vouchers.add(cacheMap.get(key));
        }

        return vouchers;
    }

    @Override
    public Voucher registerVoucher(Voucher voucher) {
        cacheMap.put(voucher.getVoucherId(), voucher);
        return voucher;
    }

    @Override
    public void deleteAll() {
        wini.clear();
        cacheMap.clear();
    }
}
