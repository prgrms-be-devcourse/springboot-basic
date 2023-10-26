package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.common.ErrorMessage;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherFactory;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.dto.VoucherDto;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("local")
public class FileVoucherRepository implements VoucherRepository {
    private static final String CSV_SEPARATOR = ",";

    private final String csvFilePath;
    private final Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(FileVoucherRepository.class);

    public FileVoucherRepository(@Value("${csv.file.voucher.path}") String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @PostConstruct
    public void init() {
        readFile();
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream().toList();
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(vouchers.get(id));
    }

    @Override
    public List<Voucher> findByName(String name) {
        return vouchers.values().stream()
                .filter(voucher -> voucher.getName().contains(name))
                .toList();
    }

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
        updateFile();
        return voucher;
    }

    @Override
    public void delete(UUID id) {
        if (!isVoucherPresent(id)) {
            throw new NoSuchElementException("Voucher not found with id: " + id);
        }
        vouchers.remove(id);
        updateFile();
    }

    public void readFile() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            while ((line = br.readLine()) != null) {
                String[] strings = line.split(CSV_SEPARATOR);
                VoucherDto.Create createDto = new VoucherDto.Create(
                        UUID.fromString(strings[0]),    // id
                        strings[1],                     // name
                        Float.parseFloat(strings[2]),   // discountAmount
                        LocalDateTime.parse(strings[3]), // createdAt
                        VoucherType.valueOf(strings[4].toUpperCase())); // voucherType

                Voucher voucher = VoucherFactory.createVoucher(createDto);
                vouchers.put(voucher.getId(), voucher);
            }
        } catch (FileNotFoundException e) {
            logger.warn(MessageFormat.format("{0} : {1}", ErrorMessage.FILE_NOT_FOUND_MESSAGE.getMessage(), csvFilePath));
        } catch (IOException e) {
            logger.error("Error occurred at FileReader: ", e);
        }
    }

    private void updateFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath))) {
            vouchers.values().stream()
                    .map(voucher -> voucher.joinInfo(CSV_SEPARATOR))
                    .forEach(line -> {
                        try {
                            bw.write(line);
                            bw.newLine();
                        } catch (IOException e) {
                            logger.error("Error occurred at FileWriter: ", e);
                        }
                    });
        } catch (IOException e) {
            logger.error("Error occurred af FileWriter: ", e);
        }
    }

    private boolean isVoucherPresent(UUID id) {
        return vouchers.containsKey(id);
    }
}
