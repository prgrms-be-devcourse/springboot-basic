package kr.co.programmers.springbootbasic.customer.service;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.customer.repository.CustomerRepository;
import kr.co.programmers.springbootbasic.customer.dto.response.CustomerResponseDto;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository userRepository;

    public CustomerService(CustomerRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<CustomerResponseDto> listAllBlackCustomer() {
        logger.info("블랙리스트 고객들을 조회합니다...");
        List<Customer> userList = userRepository.listAllBlackCustomer();

        return userList.stream()
                .map(ApplicationUtils::convertToCustomerResponseDto)
                .toList();
    }
}
