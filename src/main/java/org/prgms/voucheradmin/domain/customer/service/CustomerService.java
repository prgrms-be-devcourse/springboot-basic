package org.prgms.voucheradmin.domain.customer.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.prgms.voucheradmin.domain.customer.dao.blacklist.BlackListRepository;
import org.prgms.voucheradmin.domain.customer.dto.CustomerDto;
import org.springframework.stereotype.Service;

/**
 * 고객 조회와 관련된 로직을 담당하는 클래스입니다.
 */
@Service
public class CustomerService {
    private final BlackListRepository blackListRepository;

    public CustomerService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    /**
     * 블랙리스트를 반환 하는 메서드입니다.
     */
    public List<CustomerDto> getBlackList() throws IOException {
        List<CustomerDto> blackListedCustomers= blackListRepository.getAll().stream()
                .map(customer -> new CustomerDto(customer.getId(), customer.getName()))
                .collect(Collectors.toList());
        return blackListedCustomers;
    }
}
