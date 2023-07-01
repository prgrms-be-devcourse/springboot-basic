package com.prgms.VoucherApp.domain.customer.model;

import com.prgms.VoucherApp.domain.customer.dto.CustomerDto;
import com.prgms.VoucherApp.domain.customer.storage.BlackListFileStorage;
import com.prgms.VoucherApp.domain.customer.storage.CustomerStorage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerDao {

    private final BlackListFileStorage blackListStorage;
    private final CustomerStorage customerStorage;

    public CustomerDao(BlackListFileStorage blackListStorage, CustomerStorage customerStorage) {
        this.blackListStorage = blackListStorage;
        this.customerStorage = customerStorage;
    }

    public List<CustomerDto> readBlackLists() {
        return blackListStorage.findAll()
                .stream()
                .map(CustomerDto::new)
                .toList();
    }
}
