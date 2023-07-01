package com.devcourse.springbootbasic.application.repository.voucher;

import com.devcourse.springbootbasic.application.domain.voucher.Voucher;
import com.devcourse.springbootbasic.application.converter.VoucherConverter;
import com.devcourse.springbootbasic.application.io.CsvReader;
import com.devcourse.springbootbasic.application.io.CsvWriter;
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
    public List<Voucher> findAll() {
        return csvReader.readFile(filepath)
                .stream()
                .map(VoucherConverter::convertCsvToVoucher)
                .toList();
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
