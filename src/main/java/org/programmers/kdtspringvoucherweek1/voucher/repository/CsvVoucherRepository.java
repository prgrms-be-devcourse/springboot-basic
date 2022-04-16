package org.programmers.kdtspringvoucherweek1.voucher.repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.programmers.kdtspringvoucherweek1.error.Error;
import org.programmers.kdtspringvoucherweek1.io.Output;
import org.programmers.kdtspringvoucherweek1.log.LogLevel;
import org.programmers.kdtspringvoucherweek1.voucher.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

@Repository
@Profile("default")
public class CsvVoucherRepository implements VoucherRepository {
    private static final String PATH = "src/main/resources/data/voucherList.csv";
    private Output output;

    @Autowired
    public CsvVoucherRepository(Output output) {
        this.output = output;
    }

    @Override
    public void save(Voucher voucher) {
        File file = new File(PATH);
        try {
            FileWriter outputFile = new FileWriter(file, true);
            CSVWriter writer = new CSVWriter(outputFile);
            String[] voucherInfo = {voucher.getClass().getSimpleName(),
                    String.valueOf(voucher.getVoucherId()), String.valueOf(voucher.getDiscount())};
            writer.writeNext(voucherInfo);
            writer.close();
        } catch (Exception e) {
            output.msg(Error.FAIL_WRITE_FILE);
            output.logging(LogLevel.ERROR, e.getMessage());
        }
    }

    @Override
    public void findByIdAll() {
        try {
            FileReader fileReader = new FileReader(PATH);
            CSVReader csvReader = new CSVReader(fileReader);
            String[] nextRecord;
            StringBuilder voucher = new StringBuilder();

            while ((nextRecord = csvReader.readNext()) != null) {
                for (String record : nextRecord) {
                    voucher.append(record).append("\t");
                }
                output.msg(String.valueOf(voucher));
                voucher.delete(0, voucher.length());
            }
        } catch (Exception e) {
            output.msg(Error.FAIL_READ_FILE);
            output.logging(LogLevel.ERROR, e.getMessage());
        }
    }


}
