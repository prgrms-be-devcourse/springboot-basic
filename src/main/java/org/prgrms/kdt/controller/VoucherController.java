package org.prgrms.kdt.controller;

import java.util.List;
import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.customer.repository.BlackListRepository;
import org.prgrms.kdt.customer.service.CustomerService;
import org.prgrms.kdt.view.InputView;
import org.prgrms.kdt.view.Command;
import org.prgrms.kdt.view.OutPutView;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {

    private static final int VOUCHER_KEYWORD_INDEX = 0;
    private static final int VOUCHER_DISCOUNT_INDEX = 1;

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final BlackListRepository blackListRepository;

    public VoucherController(VoucherService voucherService, CustomerService customerService,
        BlackListRepository blackListRepository) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.blackListRepository = blackListRepository;
    }

    public void run() {
        Command command = Command.INIT;

        while (command != Command.EXIT) {
            OutPutView.showsInitMenu();
            command = InputView.getCommand();
            execute(command);
        }
    }

    private void execute(Command command) {
        switch (command) {
            case INIT:
                OutPutView.showsRetryMessage();
                break;
            case EXIT:
                System.exit(0);
            case CREATE:
                OutPutView.showsVoucherMenu();
                String[] strings = InputView.inputTheVoucher();
                VoucherType voucherMenu = VoucherType.of(strings[VOUCHER_KEYWORD_INDEX]);
                voucherService.makeVoucher(voucherMenu,
                    Long.parseLong(strings[VOUCHER_DISCOUNT_INDEX]));
                break;
            case LIST:
                List<Voucher> vouchers = voucherService.getVouchers();
                OutPutView.showVouchers(vouchers);
                break;
            case CUSTOMER:
                List<Customer> customers = customerService.getCustomers();
                OutPutView.showCustomers(customers);
                break;
        }
    }

}
