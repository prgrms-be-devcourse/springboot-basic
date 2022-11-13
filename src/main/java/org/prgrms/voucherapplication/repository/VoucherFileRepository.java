package org.prgrms.voucherapplication.repository;

import org.prgrms.voucherapplication.entity.Voucher;
import org.prgrms.voucherapplication.utils.CsvFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;

@Profile("prod")
@Repository
public class VoucherFileRepository implements VoucherRepository {

    private final File voucherFile;
    private final CsvFile csvFile;

    public VoucherFileRepository(@Value("${voucher.file-path}") String voucherFilePath, CsvFile csvFile) {
        this.voucherFile = new File(voucherFilePath);
        this.csvFile = csvFile;
    }

    @Override
    public void save(Voucher voucher) {
        String line = voucher.toString();
        csvFile.write(voucherFile, line);
    }

    @Override
    public String findAll() {
        return csvFile.readFileLines(voucherFile);
    }
}
