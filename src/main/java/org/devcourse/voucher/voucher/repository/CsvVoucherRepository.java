package org.devcourse.voucher.voucher.repository;

import com.opencsv.exceptions.CsvValidationException;
import org.devcourse.voucher.configuration.FilePathProperties;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.text.MessageFormat;
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
    private final Logger logger = LoggerFactory.getLogger(CsvVoucherRepository.class);

    public CsvVoucherRepository(FilePathProperties filePath) {
        this.filePath = filePath;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        logger.info("Repository : Record a voucher write");
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
            logger.error(MessageFormat.format("Failed to write data to file -> {0}", e.getMessage()));
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("Repository : Record a voucher read");
        List<Voucher> vouchers = new ArrayList<>();
        try (
                FileReader fileReader = new FileReader(filePath.getVoucher());
                CSVReader csvReader = new CSVReader(fileReader);
        ) {
            String[] record;

            while ((record = csvReader.readNext()) != null) {
                Voucher voucher = VoucherType.discriminate(record[VOUCHER_TYPE])
                        .voucherCreator(UUID.fromString(record[VOUCHER_ID]), Long.parseLong(record[DISCOUNT]));
                vouchers.add(voucher);
            }
        } catch (FileNotFoundException e) {
            logger.error(MessageFormat.format("File not found -> {0}", e.getMessage()));
        } catch (CsvValidationException e) {
            logger.error(MessageFormat.format("This is not a valid format CSV file -> {0}", e.getMessage()));
        } catch (IOException e) {
            logger.error(MessageFormat.format("Failed to read data from file -> {0}", e.getMessage()));
        }
        return vouchers;
    }

}
