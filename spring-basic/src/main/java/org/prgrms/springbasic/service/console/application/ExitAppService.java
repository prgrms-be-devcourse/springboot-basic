package org.prgrms.springbasic.service.console.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.repository.customer.CustomerRepository;
import org.prgrms.springbasic.repository.voucher.VoucherRepository;
import org.prgrms.springbasic.service.console.ConsoleService;
import org.springframework.stereotype.Service;

@Service("exit")
@Slf4j
@RequiredArgsConstructor
public class ExitAppService implements ConsoleService {

    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void execute() {
        log.info("program exit");
        voucherRepository.clear();
        customerRepository.clear();
        System.exit(0);
    }
}
