package prgms.spring_week1.domain.customer.service;

import org.springframework.stereotype.Service;
import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.customer.model.Customer;
import prgms.spring_week1.domain.customer.model.embeddedType.Email;
import prgms.spring_week1.domain.customer.repository.CsvRepository;
import prgms.spring_week1.domain.customer.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CsvRepository blackListRepository;
    private final CustomerRepository customerRepository;

    public CustomerService(CsvRepository blackListRepository, CustomerRepository customerRepository) {
        this.blackListRepository = blackListRepository;
        this.customerRepository = customerRepository;
    }

    public List<BlackConsumer> getBlackConsumerList() {
        return blackListRepository.getBlackConsumerList();
    }

    public void insert(String[] customerInfo){
        customerRepository.insert(new Customer(customerInfo[0],new Email(customerInfo[1])));
    }

    public List<Customer> findAll(){
        return customerRepository.findAll();
    }

    public Optional<Customer> findByEmail(Email email){
        return customerRepository.findByEmail(email.getAddress());
    }

    public void updateInfo(String[] updateEmailInfo){
        customerRepository.updateInfo(updateEmailInfo[0],updateEmailInfo[1]);
    }

    public void deleteByEmail(Email email){
        customerRepository.deleteByEmail(email.getAddress());
    }

    public void deleteAll(){
        customerRepository.deleteAll();
    }
}
