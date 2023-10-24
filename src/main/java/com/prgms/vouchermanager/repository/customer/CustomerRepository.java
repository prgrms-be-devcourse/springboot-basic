package com.prgms.vouchermanager.repository.customer;

import com.prgms.vouchermanager.domain.customer.Customer;
import com.prgms.vouchermanager.util.file.FileManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository {
    private Map<Long, Customer> blackList;

    public CustomerRepository(FileManager fileManager) {

        blackList = fileManager.readBlackListCsv();
    }

    public List<Customer> getBlackList() {

        return blackList.values()
                .stream().toList();
    }

}
