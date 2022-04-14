package org.prgrms.springbasic.service.console.application;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.repository.customer.CustomerRepository;
import org.prgrms.springbasic.repository.voucher.VoucherRepository;
import org.prgrms.springbasic.service.console.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("exit")
@Slf4j
public class ExitAppService implements ConsoleService {

    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public ExitAppService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void execute() {
        log.info("program exit");
        voucherRepository.clear();
        customerRepository.clear();
        System.exit(0);
    }
}
