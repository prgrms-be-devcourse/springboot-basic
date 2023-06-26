package com.prgms.VoucherApp.domain.customer.model;

import com.prgms.VoucherApp.domain.customer.Customer;
import com.prgms.VoucherApp.domain.customer.dto.CustomerDto;
import com.prgms.VoucherApp.domain.customer.storage.BlackListStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListReader {

    private final BlackListStorage blackListStorage;

    public BlackListReader(BlackListStorage blackListStorage) {
        this.blackListStorage = blackListStorage;
    }

    public List<CustomerDto> readBlackLists() {
        return blackListStorage.findAll()
                .stream()
                .map(Customer::convertCustomerDto)
                .toList();
    }
}
