package com.programmers.voucher.repository;

import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherType;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Profile("dev")
@Repository
public class FileVoucherRepository implements VoucherRepository {
    public static final String VOUCHER_TYPE = "type";
    public static final String VOUCHER_VALUE = "value";
    private final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private final List<Voucher> loadStore;
    private final List<Voucher> dumper;
    private final Wini wini;

    @Autowired
    public FileVoucherRepository(Wini wini) {
        this.wini = wini;
        this.loadStore = new ArrayList<>();
        this.dumper = new ArrayList<>();
    }

    @PostConstruct
    public void setting() {
        load();
    }

    @PreDestroy
    void syncToFile() {
        dump();
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

    private void load() {
        for (Entry<String, Section> sections : wini.entrySet()) {
            if (sections.getValue() != null) {
                Section section = sections.getValue();

                String id = section.getName();
                String type = section.get(VOUCHER_TYPE);
                long value = section.get(VOUCHER_VALUE, long.class);

                Voucher voucher = VoucherType.getValidateVoucherType(type)
                        .createVoucher(UUID.fromString(id), value);

                loadStore.add(voucher);
            }
        }
    }

    private void dump() {
        for (Voucher voucher : dumper) {
            UUID voucherId = voucher.getVoucherId();
            String voucherClassName = voucher.getClass()
                    .getSimpleName()
                    .replaceAll("Voucher", "");

            long voucherValue = voucher.getValue();

            wini.put(voucherId.toString(), VOUCHER_TYPE, voucherClassName);
            wini.put(voucherId.toString(), VOUCHER_VALUE, voucherValue);
        }

        try {
            logger.info("파일 동기화 시작");
            wini.store();
            logger.info("파일 동기화 완료");
        } catch (IOException e) {
            logger.error("파일 동기화 중 에러 발생", e);
        }
    }

    List<Voucher> getLoadStore() {
        return loadStore;
    }

    List<Voucher> getDumper() {
        return dumper;
    }
}
