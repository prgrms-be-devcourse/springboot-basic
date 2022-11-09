package com.programmers.voucher.repository;

import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherType;
import org.ini4j.Profile.Section;
import org.ini4j.Wini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Profile("dev")
@Repository
public class FileVoucherRepository implements VoucherRepository {
    private final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);
    private static final String VOUCHER_TYPE = "type";
    private static final String VOUCHER_VALUE = "value";
    private final List<Voucher> memoryStore = new ArrayList<>();
    private final List<Voucher> dumper = new ArrayList<>();

    private Wini wini;

    @Value("${kdt.voucher.save-path}")
    private String path;

    @PostConstruct
    public void setting() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }

        wini = new Wini(file);
        load();
    }

    @PreDestroy
    void syncToFile() {
        dump();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        return memoryStore.stream()
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
        return Stream.of(memoryStore, dumper)
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
    }

    private void load() {
        for (Entry<String, Section> sections : wini.entrySet()) {
            if (sections.getValue() != null) {
                Section section = sections.getValue();

                String id = section.getName();
                String type = section.get("type");
                long value = section.get("value", long.class);

                Voucher voucher = VoucherType.getValidateVoucherType(type)
                        .createVoucher(UUID.fromString(id), value);

                memoryStore.add(voucher);
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
}
