package org.programmers.springorder.config.file;

import org.programmers.springorder.console.Console;
import org.programmers.springorder.customer.repository.CustomerRepository;
import org.programmers.springorder.customer.repository.FileCustomerRepository;
import org.programmers.springorder.customer.service.CustomerService;
import org.programmers.springorder.voucher.repository.FileVoucherRepository;
import org.programmers.springorder.voucher.repository.VoucherRepository;
import org.programmers.springorder.voucher.service.VoucherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FileConfig {


    @Bean
    public VoucherRepository voucherRepository(Console console) {
        return new FileVoucherRepository(console);
    }

    @Bean
    public Console console(){
        return new Console();
    }


    @Bean
    public CustomerRepository customerRepository(Console console) {
        return new FileCustomerRepository(console);
    }

    @Bean
    public CustomerService customerService( CustomerRepository customerRepository, VoucherRepository voucherRepository){
        return new CustomerService(customerRepository, voucherRepository);
    }

    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository){
        return new VoucherService(voucherRepository, customerRepository);
    }


}
