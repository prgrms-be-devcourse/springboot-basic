package com.dev.voucherproject.model.storage.voucher;

import com.dev.voucherproject.model.storage.io.CsvFileReader;
import com.dev.voucherproject.model.voucher.Voucher;
import com.dev.voucherproject.model.storage.io.VoucherFileWriter;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("default")
public class CsvFileVoucherStorage implements VoucherStorage {
    @Value("${voucher.path}")
    private String path;
    @Value("${voucher.filename}")
    private String filename;
    private final CsvFileReader csvFileReader;
    private final VoucherFileWriter voucherFileWriter;

    public CsvFileVoucherStorage(CsvFileReader csvFileReader, VoucherFileWriter voucherFileWriter) {
        this.csvFileReader = csvFileReader;
        this.voucherFileWriter = voucherFileWriter;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public Voucher insert(Voucher voucher) {
        return voucherFileWriter.write(voucher);
    }

    @Override
    public List<Voucher> findAll() {
        return csvFileReader.readAllLines(path, filename).stream()
                .map(this::csvFileToVoucher)
                .toList();
    }

    private Voucher csvFileToVoucher(final String line) {
        String[] data = line.split(",");
        return Voucher.of(VoucherPolicy.valueOf(data[0]), Long.parseLong(data[1]), UUID.fromString(data[2]));
    }
}
