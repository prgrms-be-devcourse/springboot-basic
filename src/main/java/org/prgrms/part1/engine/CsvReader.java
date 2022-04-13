package org.prgrms.part1.engine;

import com.opencsv.bean.CsvToBeanBuilder;
import org.prgrms.part1.engine.domain.Customer;
import org.prgrms.part1.exception.VoucherException;
import org.prgrms.part1.io.Output;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class CsvReader implements Runnable{
    private final Output output;

    public CsvReader(Output output) {
        this.output = output;
    }

    @Override
    public void run() {
        List<Customer> blackList = getBlackList();
        printBlackList(blackList);
    }

    private List<Customer> getBlackList() {
        try {
            return new CsvToBeanBuilder<Customer>(new FileReader("blacklist.csv"))
                    .withType(Customer.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new VoucherException("There is no blacklist File!");
        }
    }

    private void printBlackList(List<Customer> blackList) {
        output.print("Show BlackList\n");
        blackList.forEach(u -> output.print(u.toString()));
    }
}
