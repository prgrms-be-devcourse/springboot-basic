package prgms.springbasic.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("customerService")
public class BlackCustomerServiceImpl implements BlackCustomerService {

    private final BlackCustomerRepository customerRepository;

    public BlackCustomerServiceImpl(BlackCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public BlackCustomer createCustomer(String name) {
        BlackCustomer newCustomer = new BlackCustomer(name);
        return customerRepository.save(newCustomer);
    }

    @Override
    public BlackCustomer findByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public List<BlackCustomer> getCustomerList() {
        return customerRepository.getCustomerList();
    }
}
