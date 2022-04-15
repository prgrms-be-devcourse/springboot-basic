package org.prgrms.kdt.service.customer;

import org.prgrms.kdt.model.customer.Customer;
import org.prgrms.kdt.repository.customer.CsvBlackListCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListCustomerService {

    private final CsvBlackListCustomerRepository csvBlackListCustomerRepository;

    public BlackListCustomerService(CsvBlackListCustomerRepository csvBlackListCustomerRepository) {
        this.csvBlackListCustomerRepository = csvBlackListCustomerRepository;
    }

    public List<Customer> findAll() {
        return csvBlackListCustomerRepository.findAll();
    }
}
