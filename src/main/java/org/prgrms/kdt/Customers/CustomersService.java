package org.prgrms.kdt.Customers;

import org.prgrms.kdt.Voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CustomersService {
    private final CustomersRepository customersRepository;
    public CustomersService(CustomersRepository customersRepository) {
        this.customersRepository=customersRepository;
    }

    public List<Customers> findAll(){
        return customersRepository.findAll();
    }

}
