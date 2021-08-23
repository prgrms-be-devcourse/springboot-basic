package org.prgrms.kdt.customer;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.prgrms.kdt.voucher.*;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CustomerService {

    public CustomerService() {
    }

    public Map<Integer, String> getBlackList(Resource resource) throws IOException {
        Map<Integer, String> data = new ConcurrentHashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(resource.getFile()))) {

            // pass header
            reader.readNext();

            String[] nextLine;

            while ((nextLine = reader.readNext()) != null) {
                int customerId = Integer.parseInt(nextLine[0]);
                String customerName = nextLine[1];
                data.put(customerId, customerName);
            }
            return data;
        } catch (FileNotFoundException | CsvValidationException e) {
            return data;
        }
    }

}
