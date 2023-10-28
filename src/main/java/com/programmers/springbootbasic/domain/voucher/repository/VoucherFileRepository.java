package com.programmers.springbootbasic.domain.voucher.repository;

import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Slf4j
@Profile("ignore")
@Repository
public class VoucherFileRepository implements VoucherRepository {

    private final String FOLDER_PATH = System.getProperty("user.dir") + File.separator + "data";
    private final String FILE_PATH;

    public VoucherFileRepository(@Value("${repository.voucher.fileName}") String fileName) {
        this.FILE_PATH = FOLDER_PATH + File.separator + fileName + ".ser";
    }

    @PostConstruct
    private void init() {
        File file = new File(FOLDER_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private void writeVouchersToFile(Map<UUID, Voucher> voucherMemory) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            outputStream.writeObject(voucherMemory);
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    private Map<UUID, Voucher> readVouchersFromFile() {
        File repositoryFile = new File(FILE_PATH);
        Map<UUID, Voucher> voucherMemory = new HashMap<>();
        if (repositoryFile.exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(repositoryFile))) {
                voucherMemory = (Map<UUID, Voucher>) inputStream.readObject();
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
        return voucherMemory;
    }

    @Override
    public Voucher save(Voucher voucher) {
        Map<UUID, Voucher> voucherMemory = readVouchersFromFile();
        voucherMemory.put(voucher.getVoucherId(), voucher);
        writeVouchersToFile(voucherMemory);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return readVouchersFromFile()
                .values()
                .stream()
                .toList();
    }

    @Override
    public void deleteAll() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            outputStream.writeObject(new HashMap<UUID, Voucher>());
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Map<UUID, Voucher> voucherMemory = readVouchersFromFile();
        return Optional.ofNullable(voucherMemory.get(voucherId));
    }

    @Override
    public void update(Voucher voucher) {
        save(voucher);
    }

    @Override
    public void delete(Voucher voucher) {
        Map<UUID, Voucher> voucherMemory = readVouchersFromFile();
        voucherMemory.remove(voucher.getVoucherId());
        writeVouchersToFile(voucherMemory);
    }
}
