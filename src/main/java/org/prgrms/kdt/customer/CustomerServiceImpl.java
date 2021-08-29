package org.prgrms.kdt.customer;

import org.prgrms.kdt.engine.OpenCsv;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    OpenCsv csvWriter = new OpenCsv();

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Map<Integer, String>> loadBlackList(Resource resource) throws IOException {
        return csvWriter.loadBlackList(resource);
    }

    @Override
    public void createCustomers(List<Customer> customers) {
        customers.forEach(customerRepository::insert);
    }
}
