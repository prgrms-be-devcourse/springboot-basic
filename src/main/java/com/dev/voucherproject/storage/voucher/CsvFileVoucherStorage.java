package com.dev.voucherproject.storage.voucher;

import com.dev.voucherproject.model.voucher.Voucher;
import com.dev.voucherproject.storage.voucher.io.VoucherFileReader;
import com.dev.voucherproject.storage.voucher.io.VoucherFileWriter;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("default")
public class CsvFileVoucherStorage implements VoucherStorage {
    private final VoucherFileReader voucherFileReader;
    private final VoucherFileWriter voucherFileWriter;

    public CsvFileVoucherStorage(VoucherFileReader voucherFileReader, VoucherFileWriter voucherFileWriter) {
        this.voucherFileReader = voucherFileReader;
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
        return voucherFileReader.readAllLines().stream()
                .map(this::csvFileApply)
                .toList();
    }

    private Voucher csvFileApply(String voucher) {
        String[] data = voucher.split(",");
        return Voucher.of(VoucherPolicy.valueOf(data[0]), Long.parseLong(data[1]), UUID.fromString(data[2]));
    }
}
