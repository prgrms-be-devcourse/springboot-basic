package org.prgrms.voucherapplication.repository;

import org.prgrms.voucherapplication.entity.Voucher;
import org.prgrms.voucherapplication.utils.CsvFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;

@Profile("prod")
@Repository
public class VoucherFileRepository implements VoucherRepository {

    private final File voucherFile;
    private final CsvFileService csvFileService;

    public VoucherFileRepository(@Value("${file-path.voucher}") String voucherFilePath, CsvFileService csvFileService) {
        this.voucherFile = new File(voucherFilePath);
        this.csvFileService = csvFileService;
    }

    @Override
    public void save(Voucher voucher) {
        String line = voucher.toString();
        csvFileService.write(voucherFile, line);
    }

    @Override
    public String findAll() {
        return csvFileService.readFileLines(voucherFile);
    }
}
