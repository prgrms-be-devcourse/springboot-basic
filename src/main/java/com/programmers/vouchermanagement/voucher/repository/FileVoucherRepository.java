package com.programmers.vouchermanagement.voucher.repository;

import com.programmers.vouchermanagement.utils.CsvFileIoManager;
import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.mapper.VoucherMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {

    private static final File FILE = new File(System.getProperty("user.dir") + "/src/main/resources/data.csv");

    private final CsvFileIoManager csvFileIoManager;
    private final Map<UUID, Voucher> storage;

    public FileVoucherRepository(CsvFileIoManager csvFileIoManager) {
        this.csvFileIoManager = csvFileIoManager;
        this.storage = addVoucherByFile(csvFileIoManager.readCsv(FILE));
    }

    @Override
    public void save(Voucher voucher) {

        storage.put(voucher.getVoucherId(), voucher);
        csvFileIoManager.writeCsv(FILE, VoucherMapper.fromEntity(voucher));
    }

    @Override
    public List<Voucher> findAll() {
        return storage.values()
                .stream()
                .toList();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.ofNullable(storage.get(voucherId));
    }

    @Override
    public void update(Voucher voucher) {

        storage.put(voucher.getVoucherId(), voucher);

        List<String> updateCsv = storage.values()
                .stream()
                .map(VoucherMapper::fromEntity)
                .toList();
        csvFileIoManager.updateCsv(FILE, updateCsv);
    }

    @Override
    public void deleteAll() {

        storage.clear();
        csvFileIoManager.updateCsv(FILE, new ArrayList<>());
    }

    private Map<UUID, Voucher> addVoucherByFile(List<String> readCsv) {

        Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

        for (String data : readCsv) {

            String[] splitData = data.split(",");

            Voucher voucher = VoucherMapper.toEntity(splitData);
            storage.put(voucher.getVoucherId(), voucher);
        }

        return storage;
    }
}
