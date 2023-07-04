package prgms.spring_week1.domain.customer.service;

import org.springframework.stereotype.Service;
import prgms.spring_week1.domain.customer.model.BlackConsumer;
import prgms.spring_week1.domain.customer.repository.BlackListRepository;

import java.util.List;

@Service
public class CustomerService {
    private final BlackListRepository blackListRepository;

    public CustomerService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public List<BlackConsumer> getBlackConsumerList() {
        return blackListRepository.getBlackConsumerList();
    }
}
