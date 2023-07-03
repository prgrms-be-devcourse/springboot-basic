package kr.co.programmers.springbootbasic.customer.service;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.customer.repository.BlackCustomerRepository;
import kr.co.programmers.springbootbasic.customer.dto.CustomerDto;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final BlackCustomerRepository userRepository;

    public CustomerService(BlackCustomerRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<CustomerDto> listAllBlackCustomer() {
        logger.info("블랙리스트 고객들을 조회합니다...");
        List<Customer> userList = userRepository.listAllBlackCustomer();

        return userList.stream()
                .map(ApplicationUtils::convertToCustomerResponseDto)
                .toList();
    }
}
