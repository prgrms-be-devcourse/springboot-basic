package org.prgms.voucher.service;

import lombok.RequiredArgsConstructor;
import org.prgms.voucher.dto.BlackCustomerResponseDto;
import org.prgms.voucher.repository.BlackCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final BlackCustomerRepository blackCustomerRepository;

    public List<BlackCustomerResponseDto> getBlackList() {
        return this.blackCustomerRepository.findAll();
    }
}
