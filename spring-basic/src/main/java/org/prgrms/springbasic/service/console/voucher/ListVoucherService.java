package org.prgrms.springbasic.service.console.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.console.Console;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.repository.voucher.VoucherRepository;
import org.prgrms.springbasic.service.console.ConsoleService;
import org.prgrms.springbasic.utils.exception.NotExistData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.prgrms.springbasic.utils.enumm.ErrorMessage.NO_VOUCHER;

@Service("list")
@Slf4j
public class ListVoucherService implements ConsoleService {

    private final VoucherRepository repository;
    private final Console console;

    @Autowired
    public ListVoucherService(VoucherRepository repository, Console console) {
        this.repository = repository;
        this.console = console;
    }

    @Override
    public void execute() {
        var vouchers = validateVouchers(repository.findAll());
        console.printData(vouchers);
    }

    private List<Voucher> validateVouchers(List<Voucher> vouchers) {
        if(vouchers.size() == 0) {
            log.error("Can't find any voucher.");
            throw new NotExistData(NO_VOUCHER.getMessage());
        }

        return vouchers;
    }
}
