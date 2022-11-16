package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.io.CSVInOut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("default")
public class FileVoucherRepository implements VoucherRepository {
    private final CSVInOut csvIO;

    public FileVoucherRepository(@Value("${voucher.path}") String path) {
        this.csvIO = new CSVInOut(path);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        csvIO.writeCSV(voucher);
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return csvIO.readCSV();
    }
}
