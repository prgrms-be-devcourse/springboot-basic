package org.prgrms.java.repository.voucher;

import org.prgrms.java.common.Mapper;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.VoucherException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository {
    private final String DATA_PATH;
    private final String DATA_NAME;

    public FileVoucherRepository(@Value("${prgrms.data.path}") String DATA_PATH, @Value("${prgrms.data.name.voucher}") String DATA_NAME) {
        File path = new File(DATA_PATH);
        if (!path.exists()) {
            path.mkdirs();
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
    public Collection<Voucher> findAll() {
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
        if (findById(voucher.getVoucherId()).isPresent() || findById(voucher.getVoucherId()).isPresent()) {
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
}
