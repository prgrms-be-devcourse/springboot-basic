package org.prgrms.kdt.domain.customer.repository;

import org.prgrms.kdt.domain.customer.model.Customer;
import org.prgrms.kdt.util.CsvUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FileCustomerRepository implements CustomerRepository {
    @Value("${csv.member.path}")
    private String csvPath;
    @Value("${csv.member.file-name}")
    private String fileName;
    private static final int UUID_INDEX = 0;
    private static final int NAME_INDEX = 1;

    @Override
    public List<Customer> findAll() {
        List<List<String>> csvData = CsvUtils.readCsv(csvPath, fileName);
        return parseCsvToList(csvData);
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
}
