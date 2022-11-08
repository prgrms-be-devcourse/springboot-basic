package org.prgrms.vouchermanagement;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.customer.service.BlackListFindService;
import org.prgrms.vouchermanagement.io.Command;
import org.prgrms.vouchermanagement.io.Input;
import org.prgrms.vouchermanagement.io.Output;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.service.VoucherCreateService;
import org.prgrms.vouchermanagement.voucher.service.VoucherListFindService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class CommandLineApplication {

    private final Input input;
    private final Output output;
    private final VoucherCreateService voucherCreateService;
    private final VoucherListFindService voucherListFindService;
    private final BlackListFindService blackListFindService;

    private final Logger logger = getLogger(CommandLineApplication.class);

    @Autowired
    public CommandLineApplication(Input input,
                                  Output output,
                                  VoucherCreateService voucherCreateService,
                                  VoucherListFindService voucherListFindService,
                                  BlackListFindService blackListFindService) {
        this.input = input;
        this.output = output;
        this.voucherCreateService = voucherCreateService;
        this.voucherListFindService = voucherListFindService;
        this.blackListFindService = blackListFindService;
    }

    void run() {

        Command command = Command.NONE;
        while (command.isNotExit()) {
            try {
                output.printCommandNotices();
                command = Command.findCommand(input.receiveCommand());
                switch (command) {
                    case CREATE:
                        String voucherTypeInput = input.receiveVoucherType();
                        int voucherAmountInput = input.receiveDiscountAmount(voucherTypeInput);
                        voucherCreateService.createVoucher(voucherTypeInput, voucherAmountInput);
                        output.printVoucherCreateMessage();
                        break;
                    case LIST:
                        List<Voucher> vouchers = voucherListFindService.findAllVouchers();
                        output.printAllVouchers(vouchers);
                        break;
                    case BLACKLIST:
                        List<Customer> blackList = blackListFindService.findAllBlackList();
                        output.printAllBlackList(blackList);
                        break;
                    case EXIT:
                        command = Command.EXIT;
                }
            } catch (RuntimeException e) {

                logger.error("예외 발생 : {}", e.getMessage());

                System.out.println(e.getMessage());
            }
        }
    }
}
