package org.prgrms.kdtspringdemo.domain.blacklist.repository;

import org.prgrms.kdtspringdemo.domain.blacklist.model.BlackCustomer;
import org.prgrms.kdtspringdemo.io.file.CSVReader;
import org.prgrms.kdtspringdemo.io.file.CsvDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile({"default", "dev"})
public class FileBlackCustomerRepository implements BlackCustomerRepository {
    private static final Logger logger = LoggerFactory.getLogger(FileBlackCustomerRepository.class);//static이 있어야 하는 이유를 확인
    private final CSVReader csvReader;
    private final Map<UUID, BlackCustomer> storage = new ConcurrentHashMap<>();

    public FileBlackCustomerRepository(@Value("${customer.path}") String path) {
        csvReader = new CSVReader(path);
    }

    @PostConstruct
    public void initStorage() {
        try {
            CsvDto csvDto = csvReader.readCSV();
            for (String[] strings : csvDto.value) {
                BlackCustomer blackCustomer = makeCustomerFromCsvLine(strings);
                storage.put(blackCustomer.getCustomerId(), blackCustomer);
            }
        } catch (FileNotFoundException e) {
            logger.error("[black_list load fail] customer_blacklist.csv가 있는지 확인해 주세요");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private BlackCustomer makeCustomerFromCsvLine(String[] strings) {
        UUID customerId = UUID.fromString(strings[0]);
        String email = strings[1];
        LocalDate dateOfBirth = LocalDate.parse(strings[2]);
        return new BlackCustomer(customerId, email, dateOfBirth);
    }

    public List<BlackCustomer> findAllBlackCustomers() {
        return new ArrayList<>(storage.values());
    }
}
