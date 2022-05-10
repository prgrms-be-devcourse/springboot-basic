package com.voucher.vouchermanagement.service.blacklist;

import com.voucher.vouchermanagement.dto.customer.CustomerDto;
import com.voucher.vouchermanagement.repository.blacklist.BlacklistFileRepository;
import com.voucher.vouchermanagement.repository.blacklist.BlacklistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
// TODO : Blacklist 테이블을 따로 만들던지, Customer에 Blacklist 등록 데이터를 추가해서 필터링 하던지 해서 구현해야됨.
public class BlacklistService {

    private final BlacklistRepository blacklistRepository;

    public BlacklistService(BlacklistFileRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public List<CustomerDto> findAll() {
        return this.blacklistRepository.findAll()
                .stream()
                .map(CustomerDto::of)
                .collect(Collectors.toList());
    }
}
