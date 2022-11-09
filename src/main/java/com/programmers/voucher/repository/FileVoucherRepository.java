package com.programmers.voucher.repository;

import com.programmers.voucher.repository.dumper.Dumper;
import com.programmers.voucher.repository.loader.Loader;
import com.programmers.voucher.voucher.Voucher;
import org.ini4j.Wini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Profile("dev")
@Repository
public class FileVoucherRepository implements VoucherRepository {
    public static final String VOUCHER_TYPE = "type";
    public static final String VOUCHER_VALUE = "value";
    private final List<Voucher> loadStore;
    private final List<Voucher> dumper;
    private final Wini wini;

    private final Loader loader;
    private final Dumper dump;

    @Autowired
    public FileVoucherRepository(Wini wini, Loader loader, Dumper dump) {
        this.wini = wini;
        this.loader = loader;
        this.dump = dump;

        this.loadStore = new ArrayList<>();
        this.dumper = new ArrayList<>();
    }

    @PostConstruct
    public void setting() {
        loader.load(loadStore);
    }

    @PreDestroy
    void syncToFile() {
        dump.dump(dumper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        return loadStore.stream()
                .filter(memoryVoucher -> memoryVoucher.getVoucherId().equals(voucherId))
                .findAny()
                .or(() ->
                        dumper.stream()
                                .filter(dumperVoucher -> dumperVoucher.getVoucherId().equals(voucherId))
                                .findAny()
                );
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return Stream.of(loadStore, dumper)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Voucher registerVoucher(Voucher voucher) {
        dumper.add(voucher);
        return voucher;
    }

    @Override
    public void deleteAll() {
        wini.clear();
        loadStore.clear();
        dumper.clear();
    }

    List<Voucher> getLoadStore() {
        return loadStore;
    }

    List<Voucher> getDumper() {
        return dumper;
    }
}
