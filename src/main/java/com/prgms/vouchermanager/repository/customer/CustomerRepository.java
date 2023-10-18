package com.prgms.vouchermanager.repository.customer;

import com.prgms.vouchermanager.domain.customer.Customer;
import com.prgms.vouchermanager.util.file.FileManager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository {

    private final FileManager fileManager;
    private Map<Long, Customer> blackList;

    public CustomerRepository(FileManager fileManager) {

        this.fileManager = fileManager;
        blackList = fileManager.readBlackListCsv();
    }

    public List<Customer> getBlackList() {
         List<Customer>customerList= new ArrayList<>(blackList.values());
         return customerList;
    }

}
