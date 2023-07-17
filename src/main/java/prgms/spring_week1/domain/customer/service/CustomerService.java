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

    public void insert(String name, Email email) {
        customerRepository.insert(new Customer(name, email));
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findByEmail(Email email) {
        return customerRepository.findByEmail(email.getAddress());
    }

    public void updateInfo(String[] updateEmailInfo) {
        String beforeUpdateEmail = updateEmailInfo[0];
        String afterUpdateEmail = updateEmailInfo[1];

        customerRepository.updateInfo(beforeUpdateEmail, afterUpdateEmail);
    }

    public void deleteByEmail(Email email) {
        customerRepository.deleteByEmail(email.getAddress());
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
