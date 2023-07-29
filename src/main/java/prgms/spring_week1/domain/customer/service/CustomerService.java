package prgms.spring_week1.domain.customer.service;

import org.springframework.stereotype.Service;
import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.customer.model.Customer;
import prgms.spring_week1.domain.customer.model.embeddedType.Email;
import prgms.spring_week1.domain.customer.repository.CsvRepository;
import prgms.spring_week1.domain.customer.repository.CustomerRepository;
import prgms.spring_week1.exception.NoCustomerFoundException;

import java.util.List;

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
        Customer customer = customerRepository.findByEmail(email.getAddress());
        boolean customerNotExist = customer == null;

        if(customerNotExist){
            throw new NoCustomerFoundException("해당 이메일로 조회 된 회원이 없습니다.");
        }

        return customer;
    }

    public void updateInfo(String beforeUpdateEmail,String afterUpdateEmail) {
        customerRepository.updateInfo(beforeUpdateEmail, afterUpdateEmail);
    }

    public void deleteByEmail(Email email) {
        customerRepository.deleteByEmail(email.getAddress());
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
