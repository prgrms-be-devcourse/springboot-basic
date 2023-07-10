package kr.co.programmers.springbootbasic.customer.service;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.customer.dto.CustomerResponse;
import kr.co.programmers.springbootbasic.customer.repository.BlackCustomerRepository;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("web")
public class BlackCustomerService {
    private static final Logger logger = LoggerFactory.getLogger(BlackCustomerService.class);
    private final BlackCustomerRepository blackCustomerRepository;

    public BlackCustomerService(BlackCustomerRepository userRepository) {
        this.blackCustomerRepository = userRepository;
    }

    public List<CustomerResponse> listAllBlackCustomer() {
        logger.info("블랙리스트 고객들을 조회합니다...");
        List<Customer> userList = blackCustomerRepository.listAllBlackCustomer();

        return userList.stream()
                .map(ApplicationUtils::convertToCustomerResponse)
                .toList();
    }
}
