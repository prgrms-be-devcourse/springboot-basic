package com.programmers.vouchermanagement.repository.voucher;

import com.programmers.vouchermanagement.common.ErrorMessage;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherFactory;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("file")
public class FileVoucherRepository implements VoucherRepository {
    private final Map<UUID, Voucher> vouchers = new ConcurrentHashMap<>();
    private final String csvFilePath;
    private final String csvSeparator;

    public FileVoucherRepository(@Value("${csv.file.voucher.path}") String csvFilePath, @Value("${csv.separator}") String csvSeparator) {
        this.csvFilePath = csvFilePath;
        this.csvSeparator = csvSeparator;
    }

    @PostConstruct
    public void init() {
        try {
            readFile();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Voucher> findAll() {
        return vouchers.values().stream().toList();
    }

    @Override
    public Voucher save(Voucher voucher) {
        vouchers.put(voucher.getId(), voucher);
        updateFile();
        return voucher;
    }

    public void readFile() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            while ((line = br.readLine()) != null) {
                String[] strings = line.split(csvSeparator);
                Voucher voucher = VoucherFactory.createVoucher(
                        UUID.fromString(strings[0]),
                        strings[1],
                        Float.parseFloat(strings[2]),
                        VoucherType.valueOf(strings[3].toUpperCase()));
                vouchers.put(voucher.getId(), voucher);
            }
        } catch (FileNotFoundException e) {
            throw new NoSuchElementException(ErrorMessage.FILE_NOT_FOUND_MESSAGE.getMessage());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void updateFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath))) {
            vouchers.values().stream()
                    .map(voucher -> voucher.joinInfo(csvSeparator))
                    .forEach(line -> {
                        try {
                            bw.write(line);
                            bw.newLine();
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        } catch (FileNotFoundException e) {
            throw new NoSuchElementException(ErrorMessage.FILE_NOT_FOUND_MESSAGE.getMessage());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
