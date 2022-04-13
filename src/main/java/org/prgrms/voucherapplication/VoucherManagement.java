package org.prgrms.voucherapplication;

import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.entity.Voucher;
import org.prgrms.voucherapplication.service.CustomerService;
import org.prgrms.voucherapplication.service.VoucherService;
import org.prgrms.voucherapplication.view.Console;
import org.prgrms.voucherapplication.view.io.Menu;
import org.prgrms.voucherapplication.view.io.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherManagement implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(VoucherManagement.class);

    private final Console console;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public VoucherManagement(Console console, VoucherService voucherService, CustomerService customerService) {
        this.console = console;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Menu selectedMenu = console.inputMenu();
                switch (selectedMenu) {
                    case EXIT -> System.exit(0);
                    case CREATE -> {
                        VoucherType voucherType = console.inputVoucherType();
                        long discountValue = console.inputDiscount(voucherType);
                        Voucher voucher = voucherService.createVoucher(voucherType, discountValue);
                        voucherService.saveVoucher(voucher);
                    }
                    case LIST -> {
                        List<Voucher> allVoucher = voucherService.getAllVoucher();
                        console.printVoucherList(allVoucher);
                    }
                    case BLACKLIST -> {
                        List<Customer> customerList = customerService.getAllCustomer();
                        console.printBlackList(customerList);
                    }
                    default -> logger.error("Invalid Menu type in switch state");
                }

            } catch (Exception e) {
                logger.error(e.getMessage());
            }

        }
    }
}
