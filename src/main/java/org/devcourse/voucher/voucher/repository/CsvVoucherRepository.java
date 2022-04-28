package org.devcourse.voucher.voucher.repository;

import org.devcourse.voucher.configuration.FilePathProperties;
import org.devcourse.voucher.voucher.model.Voucher;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

@Repository
public class CsvVoucherRepository implements VoucherRepository {

    private final FilePathProperties filePath;

    public CsvVoucherRepository(FilePathProperties filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Voucher voucher) {
        File file = new File(filePath.getVoucher());
        try (
                FileWriter outputFile = new FileWriter(file, true);
                CSVWriter writer = new CSVWriter(outputFile);
        ) {
            String[] voucherInfo = {voucher.getClass().getSimpleName(),
                    String.valueOf(voucher.getVoucherId()), String.valueOf(voucher.getDiscount())
            };
            writer.writeNext(voucherInfo);
        } catch (IOException e) {
            // logging
        }
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try (
                FileReader fileReader = new FileReader(filePath.getVoucher());
                CSVReader csvReader = new CSVReader(fileReader);
        ) {

        } catch (Exception e) {
//            e.printStackTrace();
        }
        return vouchers;
    }

}
