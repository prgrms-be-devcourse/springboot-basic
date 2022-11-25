package org.prgrms.java.repository.voucher;

import org.prgrms.java.common.Mapper;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.VoucherException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FileVoucherRepository implements VoucherRepository {
    private final String DATA_PATH;
    private final String DATA_NAME;

    public FileVoucherRepository(@Value("${prgrms.data.path}") String DATA_PATH, @Value("${prgrms.data.name.voucher}") String DATA_NAME) {
        try {
            new File(DATA_PATH).mkdirs();
            new File(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME)).createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.DATA_PATH = DATA_PATH;
        this.DATA_NAME = DATA_NAME;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME)))) {
            Optional<String> str = reader.lines()
                    .filter(line -> line.contains(voucherId.toString()))
                    .findAny();
            if (str.isPresent()) {
                return Optional.of(Mapper.mapToVoucher(str.get()));
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Voucher> findByCustomer(UUID customerId) {
        return Collections.emptyList();
    }

    @Override
    public List<Voucher> findExpiredVouchers() {
        return Collections.emptyList();
    }

    @Override
    public List<Voucher> findAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME)))) {
            return reader.lines()
                    .map(Mapper::mapToVoucher)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {
        if (findById(voucher.getVoucherId()).isPresent()) {
            throw new VoucherException(String.format("Already exists voucher having id %s", voucher.getVoucherId()));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME), true))) {
            writer.write(voucher.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        return null;
    }

    @Override
    public void delete(UUID voucherId) {

    }

    @Override
    public long deleteAll() {
        String fileName = MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME);
        long count;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            count = reader.lines()
                    .filter(line -> !line.isBlank())
                    .count();
            new FileWriter(fileName).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return count;
    }
}
