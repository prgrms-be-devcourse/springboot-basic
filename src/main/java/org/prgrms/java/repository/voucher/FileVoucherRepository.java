package org.prgrms.java.repository.voucher;

import org.prgrms.java.common.Mapper;
import org.prgrms.java.domain.voucher.Voucher;
import org.prgrms.java.exception.VoucherException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.io.*;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Primary
public class FileVoucherRepository implements VoucherRepository {
    private final String DATA_PATH = "data";
    private final String DATA_NAME = "voucher.csv";
    private final BufferedWriter writer;
    private final BufferedReader reader;

    public FileVoucherRepository() throws IOException {
        File path = new File(DATA_PATH);
        if (!path.exists()) {
            path.mkdirs();
        }

        writer = new BufferedWriter(new FileWriter(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME), true));
        reader = new BufferedReader(new FileReader(MessageFormat.format("{0}/{1}", DATA_PATH, DATA_NAME)));
    }

    @PreDestroy
    private void close() {
        try {
            writer.close();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.of(
                Mapper.mapToVoucher(
                        reader.lines()
                                .filter(line -> line.contains(voucherId.toString()))
                                .findAny()
                                .orElseThrow()));
    }

    @Override
    public Collection<Voucher> findAll() {
        return reader.lines()
                .map(Mapper::mapToVoucher)
                .collect(Collectors.toList());
    }

    @Override
    public Voucher insert(Voucher voucher) {
        if (findById(voucher.getVoucherId()).isPresent() || findById(voucher.getVoucherId()).isPresent()) {
            throw new VoucherException(String.format("Already exists voucher having id %s", voucher.getVoucherId()));
        }
        try {
            writer.write(voucher.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return voucher;
    }
}
