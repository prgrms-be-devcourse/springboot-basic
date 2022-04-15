package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.supercsv.io.CsvMapReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.commons.lang3.math.NumberUtils.toLong;

@Primary
@Qualifier("csv")
@Repository
public class CsvVoucherRepository implements VoucherRepository {

    private static final String CSV_FILENAME = "voucher.csv";
    private static final String CSV_PATH = System.getProperty("user.dir");
    private static final String[] CSV_HEADER = {"voucherId", "voucherValue", "voucherClassSimpleName"};

    private static final Logger logger = LoggerFactory.getLogger(CsvVoucherRepository.class);

    @Override
    public Voucher insert(Voucher voucher) {
        try (ICsvMapWriter beanWriter = new CsvMapWriter(new FileWriter(getPathCsvFile(), true), CsvPreference.STANDARD_PREFERENCE)) {

            Map<String, String> map = new ConcurrentHashMap<>();
            map.put(CSV_HEADER[0], voucher.getVoucherId().toString());
            map.put(CSV_HEADER[1], String.valueOf(voucher.getVoucherValue()));
            map.put(CSV_HEADER[2], voucher.getClass().getSimpleName());

            beanWriter.write(map, CSV_HEADER);
        } catch (IOException e) {
            logger.error("failed to save voucher : {}", e.getMessage(), e);
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        List<Voucher> vouchers = new ArrayList<>();
        try (ICsvMapReader beanReader = new CsvMapReader(new FileReader(getPathCsvFile()), CsvPreference.STANDARD_PREFERENCE)) {

            Map<String, String> readData;
            while ((readData = beanReader.read(CSV_HEADER)) != null) {
                UUID voucherId = UUID.fromString(readData.get(CSV_HEADER[0]));
                long voucherValue = toLong(readData.get(CSV_HEADER[1]));
                String classSimpleName = readData.get(CSV_HEADER[2]);

                vouchers.add(VoucherFactory.create(voucherId, voucherValue, classSimpleName));
            }
        } catch (IOException e) {
            logger.error("failed to get vouchers in csv-file : {}", e.getMessage(), e);
        }
        return vouchers;
    }

    public String getPathCsvFile() {
        return Path.of(CSV_PATH, CSV_FILENAME).toString();
    }
}
