package org.prgrms.voucherapplication;

import org.prgrms.voucherapplication.entity.BlackListCustomer;
import org.prgrms.voucherapplication.entity.Customer;
import org.prgrms.voucherapplication.entity.Voucher;
import org.prgrms.voucherapplication.service.BlackListCustomerService;
import org.prgrms.voucherapplication.service.JdbcCustomerService;
import org.prgrms.voucherapplication.service.JdbcVoucherService;
import org.prgrms.voucherapplication.service.VoucherService;
import org.prgrms.voucherapplication.view.Console;
import org.prgrms.voucherapplication.view.io.Menu;
import org.prgrms.voucherapplication.view.io.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 바우처 애플리케이션을 실행할 수 있는 class
 */
@Component
public class VoucherManagement implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(VoucherManagement.class);

    private final Console console;
    private final VoucherService voucherService;
    private final BlackListCustomerService blackListCustomerService;
    private final JdbcCustomerService jdbcCustomerService;
    private final JdbcVoucherService jdbcVoucherService;

    public VoucherManagement(Console console, VoucherService voucherService, BlackListCustomerService blackListCustomerService, JdbcCustomerService jdbcCustomerService, JdbcVoucherService jdbcVoucherService) {
        this.console = console;
        this.voucherService = voucherService;
        this.blackListCustomerService = blackListCustomerService;
        this.jdbcCustomerService = jdbcCustomerService;
        this.jdbcVoucherService = jdbcVoucherService;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Menu selectedMenu = console.inputMenu();
                if(selectedMenu.equals(Menu.EXIT)) {
                    break;
                }
                switch (selectedMenu) {
                    case CREATE_VOUCHER_FILE -> {
                        VoucherType voucherType = console.inputVoucherType();
                        long discountValue = console.inputDiscount(voucherType);
                        Voucher voucher = voucherService.createVoucher(voucherType, discountValue);
                        voucherService.saveVoucher(voucher);
                    }
                    case LIST_VOUCHER_FILE -> {
                        List<Voucher> allVoucher = voucherService.getAllVoucher();
                        console.printVoucherList(allVoucher);
                    }
                    case BLACKLIST -> {
                        List<BlackListCustomer> customerList = blackListCustomerService.getAllBlackListCustomer();
                        console.printBlackList(customerList);
                    }
                    case CREATE_CUSTOMER -> {
                        Customer customer = console.inputCustomerInformation();
                        jdbcCustomerService.saveCustomer(customer);
                    }
                    default -> logger.error("Invalid Menu type in switch state");
                }

            } catch (Exception e) {
                logger.error(e.getMessage());
            }

        }
    }
}
