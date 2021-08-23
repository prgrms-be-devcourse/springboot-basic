package org.prgrms.kdt.customer;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.prgrms.kdt.engine.OpenCsv;
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

    OpenCsv csvWriter = new OpenCsv();

    public CustomerService() {
    }

    public Optional<Map<Integer, String>> loadBlackList(Resource resource) throws IOException {
        return csvWriter.loadBlackList(resource);
    }
}
