package com.devcourse.springbootbasic.application.voucher.repository;

import com.devcourse.springbootbasic.application.voucher.VoucherConverter;
import com.devcourse.springbootbasic.application.voucher.model.Voucher;
import com.devcourse.springbootbasic.application.global.io.CsvReader;
import com.devcourse.springbootbasic.application.global.io.CsvWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile({"default"})
public class FileVoucherRepository implements VoucherRepository {

    @Value("${settings.voucherRecordPath}")
    private String filepath;

    private final CsvReader csvReader;
    private final CsvWriter csvWriter;

    public FileVoucherRepository(CsvReader csvReader, CsvWriter csvWriter) {
        this.csvReader = csvReader;
        this.csvWriter = csvWriter;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        return csvWriter.writeFile(filepath, voucher);
    }

    @Override
    public List<Voucher> findAllVouchers() {
        return csvReader.readFile(filepath)
                .stream()
                .map(VoucherConverter::convertCsvToVoucher)
                .toList();
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
