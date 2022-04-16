package org.programmers.kdtspringvoucherweek1.blacklist.repository;

import com.opencsv.CSVReader;
import org.programmers.kdtspringvoucherweek1.error.Error;
import org.programmers.kdtspringvoucherweek1.io.Output;
import org.programmers.kdtspringvoucherweek1.log.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileReader;

@Repository
public class CsvBlacklistRepository implements BlacklistRepository {
    private static final String PATH = "src/main/resources/data/customer_blacklist.csv";
            private final Output output;

    @Autowired
    public CsvBlacklistRepository(Output output) {
                this.output = output;
            }
        @Override
        public void findByIdAll() {
            try {
                FileReader fileReader = new FileReader(PATH);
                CSVReader csvReader = new CSVReader(fileReader);
                String[] nextRecord;

                while ((nextRecord = csvReader.readNext()) != null) {
                    for (String record : nextRecord) {
                        output.msg(record);
                    }
                }
            } catch (Exception e) {
                output.msg(Error.FAIL_READ_FILE);
                output.logging(LogLevel.ERROR, e.getMessage());
            }
        }
}
