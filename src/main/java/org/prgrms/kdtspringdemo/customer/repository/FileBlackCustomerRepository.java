package org.prgrms.kdtspringdemo.customer.repository;

import org.prgrms.kdtspringdemo.customer.Customer;
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
@Profile({"default","dev"})
public class FileBlackCustomerRepository {
    private final CSVReader csvReader;
    private final Map<UUID, Customer> storage= new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(FileBlackCustomerRepository.class);

    public FileBlackCustomerRepository(@Value("${customer.path}") String path) {
        csvReader = new CSVReader(path);

    }

    @PostConstruct
    public void initStorage(){
        try{
            CsvDto csvDto = csvReader.readCSV();
            for (String[] strings : csvDto.value) {
                Customer customer = makeCustomerFromCsvLine(strings);
                storage.put(customer.getCustomerId(),customer);

            }
        } catch (FileNotFoundException e) {
            logger.error("[black_list load fail] customer_blacklist.csv가 있는지 확인해 주세요");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private Customer makeCustomerFromCsvLine(String[] strings) {
        UUID customerId = UUID.fromString(strings[0]);
        String name = strings[1];
        LocalDate dateOfBirth = LocalDate.parse(strings[2]);
        return new Customer(customerId,name,dateOfBirth);
    }

    public List<Customer> findAllBlackCustomer(){
        return new ArrayList<>(storage.values());
    }
}
