package com.prgrms.devkdtorder.customervoucher.repository;

import com.prgrms.devkdtorder.customer.domain.BlackCustomers;
import com.prgrms.devkdtorder.customer.domain.Customer;
import com.prgrms.devkdtorder.customervoucher.domain.CustomerVoucher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerVoucherRepository {

    CustomerVoucher insert(CustomerVoucher customerVoucher);

    CustomerVoucher update(CustomerVoucher customerVoucher);

    List<CustomerVoucher> findAll();

    Optional<CustomerVoucher> findById(UUID customerVoucherId);

    void deleteAll();

}
