package org.prgms.springbootbasic.service;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.repository.customervouchermanagement.CustomerVoucherManagementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class CustomerVoucherManagementService {
    private final CustomerVoucherManagementRepository managementRepository;

    public CustomerVoucherManagementService(CustomerVoucherManagementRepository managementRepository) {
        this.managementRepository = managementRepository;
    }

    public void allocate(UUID customerId, UUID voucherId){
        managementRepository.allocateVoucherById(customerId, voucherId);
    }

    public void delete(UUID customerId, UUID voucherId){
        managementRepository.deleteVoucherById(customerId, voucherId);
    }

    public List<Voucher> searchVouchersFromCustomer(UUID customerId){
        return managementRepository.searchVouchersByCustomerId(customerId);
    }

    public List<Customer> searchCustomerFromVoucher(UUID voucherId){
        return managementRepository.searchCustomersByVoucherId(voucherId);
    }
}
