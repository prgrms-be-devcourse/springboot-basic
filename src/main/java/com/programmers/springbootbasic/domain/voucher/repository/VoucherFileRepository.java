package com.programmers.springbootbasic.domain.voucher.repository;

import com.programmers.springbootbasic.domain.voucher.entity.Voucher;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Profile("default")
@Repository
public class VoucherFileRepository implements VoucherRepository {

    private final String FOLDER_PATH = System.getProperty("user.dir") + "/data";
    private String filePath;

    @Value("${repository.voucher.fileName}")
    private String fileName;

    @PostConstruct
    private void init() {
        this.filePath = FOLDER_PATH + "/" + fileName + ".ser";
        File file = new File(FOLDER_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private void writeVoucherToFile(Voucher voucher) {
        Map<UUID, Voucher> voucherMemory = readVouchersFromFile();
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            voucherMemory.put(voucher.getVoucherId(), voucher);
            outputStream.writeObject(voucherMemory);
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    private Map<UUID, Voucher> readVouchersFromFile() {
        Map<UUID, Voucher> voucherMemory = new HashMap<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            voucherMemory = (Map<UUID, Voucher>) inputStream.readObject();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return voucherMemory;
    }

    @Override
    public Voucher save(Voucher voucher) {
        writeVoucherToFile(voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return readVouchersFromFile()
                .values()
                .stream()
                .toList();
    }
}
