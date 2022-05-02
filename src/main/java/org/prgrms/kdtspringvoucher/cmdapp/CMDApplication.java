package org.prgrms.kdtspringvoucher.cmdapp;

import org.prgrms.kdtspringvoucher.cmdapp.console.Console;
import org.prgrms.kdtspringvoucher.customer.CustomerService;
import org.prgrms.kdtspringvoucher.voucher.service.Voucher;
import org.prgrms.kdtspringvoucher.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CMDApplication implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(CMDApplication.class);
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final Console console;

    public CMDApplication(VoucherService voucherService, CustomerService customerService, Console console) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.console = console;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
                ServiceType command = console.getServiceType();
                switch (command) {
                    case EXIT:
                        logger.info("Voucher Program exit");
                        flag = false;
                        break;
                    case LIST:
                        logger.info("Run Voucher List");
                        console.printList(voucherService.getAllVouchers());
                        break;
                    case CREATE:
                        logger.info("Run Create Voucher");
                        Voucher voucher = console.getVoucher();
                        if (voucher == null) {
                            System.out.println("Select Voucher Type Code 1 or 2");
                            break;
                        }
                        voucherService.saveVoucher(voucher);
                        break;
                    case BLACKLIST:
                        logger.info("Run Black Customer List");
                        console.printList(customerService.getAllCustomers());
                        break;
                }
        }
    }
}
