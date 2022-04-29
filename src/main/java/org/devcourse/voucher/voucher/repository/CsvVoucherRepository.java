package org.devcourse.voucher.voucher.repository;

import com.opencsv.exceptions.CsvValidationException;
import org.devcourse.voucher.configuration.FilePathProperties;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

@Repository
@Primary
public class CsvVoucherRepository implements VoucherRepository {

    private final FilePathProperties filePath;
    private static final int VOUCHER_TYPE = 0;
    private static final int VOUCHER_ID = 1;
    private static final int DISCOUNT = 2;

    public CsvVoucherRepository(FilePathProperties filePath) {
        this.filePath = filePath;
    }

    @Override
    public Voucher insert(Voucher voucher) {
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
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try (
                FileReader fileReader = new FileReader(filePath.getVoucher());
                CSVReader csvReader = new CSVReader(fileReader);
        ) {
            String[] record;

            while((record = csvReader.readNext()) != null) {
                Voucher voucher = VoucherType.discriminate(record[VOUCHER_TYPE])
                        .voucherCreator(UUID.fromString(record[VOUCHER_ID]), Long.parseLong(record[DISCOUNT]));
                vouchers.add(voucher);
            }
        } catch (CsvValidationException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return vouchers;
    }

}
