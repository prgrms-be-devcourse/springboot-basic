package prgms.springbasic.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import prgms.springbasic.io.Printer;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("customerService")
public class BlackCustomerServiceImpl implements BlackCustomerService {

    private final BlackCustomerRepository customerRepository;

    public BlackCustomerServiceImpl(BlackCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public BlackCustomer saveCustomer(String name) {
        BlackCustomer newCustomer = new BlackCustomer(name);
        return customerRepository.save(newCustomer);
    }

    @Override
    public Optional<BlackCustomer> findByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
    public List<BlackCustomer> getCustomerList() {
        return customerRepository.getCustomerList();
    }

    @Override
    public void listIsEmpty() {
        Printer printer = new Printer();
        printer.printBlackCustomerListEmpty();
    }
}
