package org.prgrms.kdt.domain.customer.repository;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.util.CsvUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    @Value("${csv.member.path}")
    private String csvPath;
    @Value("${csv.member.file-name}")
    private String fileName;
    private final Logger logger = LoggerFactory.getLogger(FileCustomerRepository.class);
    private static final int UUID_INDEX = 0;
    private static final int NAME_INDEX = 1;

    @Override
    public List<Customer> findAll() {
        List<List<String>> csvData;
        try {
            csvData = CsvUtils.readCsv(csvPath, fileName);
        } catch (IOException e) {
            logger.error("Save file voucher error, {}", e.getMessage());
            throw new IllegalArgumentException("파일의 경로 혹은 이름을 확인해주세요.");
        }
        return parseCsvToList(csvData);
    }

    @Override
    public UUID save(Customer customer) {
        String data = createCsvData(customer);
        try {
            CsvUtils.writeCsv(csvPath, fileName, data);
        } catch (IOException e) {
            logger.error("Save file voucher error, {}", e.getMessage());
            throw new IllegalArgumentException("파일의 경로 혹은 이름을 확인해주세요.");
        }
        return customer.getCustomerId();
    }

    private List<Customer> parseCsvToList(List<List<String>> csvData) {
        List<Customer> members = new ArrayList<>();
        for (List<String> row : csvData) {
            UUID customerId = UUID.fromString(row.get(UUID_INDEX));
            String name = row.get(NAME_INDEX);
            members.add(new Customer(customerId, name));
        }
        return members;
    }

    private String createCsvData(Customer customer) {
        StringBuilder data = new StringBuilder(customer.getCustomerId().toString())
                .append(",")
                .append(customer.getName());
        return data.toString();
    }
}
