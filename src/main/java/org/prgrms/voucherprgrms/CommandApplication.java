package org.prgrms.voucherprgrms;

import org.prgrms.voucherprgrms.customer.CustomerService;
import org.prgrms.voucherprgrms.customer.model.Customer;
import org.prgrms.voucherprgrms.io.InputConsole;
import org.prgrms.voucherprgrms.io.OutputConsole;
import org.prgrms.voucherprgrms.voucher.VoucherService;
import org.prgrms.voucherprgrms.voucher.model.Voucher;
import org.prgrms.voucherprgrms.voucher.model.VoucherForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandApplication implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(CommandApplication.class);

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final InputConsole inputConsole;
    private final OutputConsole outputConsole;

    public CommandApplication(VoucherService voucherService, CustomerService customerService, InputConsole inputConsole, OutputConsole outputConsole) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean runnableFlag = true;
        while (runnableFlag) {
            String input = inputConsole.commandInput();
            try {
                switch (input) {
                    case "exit":
                        runnableFlag = false;
                        break;
                    case "create":
                        createVoucher();
                        break;
                    case "list":
                        printVoucherList();
                        break;
                    case "allocate":
                        allocateVoucher();
                        break;
                    case "remove":
                        removeVoucherFromCustomer();
                        break;
                    default:
                        outputConsole.commandErrorMessage();
                }
            } catch (IllegalArgumentException e) {
                logger.error("Command App ERROR (Illegal Argu)", e);
                outputConsole.commandErrorMessage();
            } catch (DuplicateKeyException e) {
                logger.error("Command App ERROR (Duplicate Key)", e);
                outputConsole.sqlErrorMessage();
            }
        }
    }

    private void removeVoucherFromCustomer() {
        List<Customer> customerList = printCustomerList();
        Customer selectedCustomer = customerList.get(inputConsole.getSelect());

        customerService.removeVoucher(selectedCustomer);
    }

    private void allocateVoucher() {
        List<Customer> customerList = printCustomerList();
        Customer selectedCustomer = customerList.get(inputConsole.getSelect());

        List<Voucher> voucherList = printVoucherList();
        Voucher selectedVoucher = voucherList.get(inputConsole.getSelect());
        //can't allocate
        if (!customerService.allocateVoucher(selectedCustomer, selectedVoucher))
            outputConsole.failedAllocation();
    }

    private List<Customer> printCustomerList() {
        List<Customer> list = customerService.findAllCustomer();
        outputConsole.customerList(list);
        return list;
    }

    private List<Voucher> printVoucherList() {
        List<Voucher> list = voucherService.findAllVoucher();
        outputConsole.voucherList(list);
        return list;
    }

    private void createVoucher() {
        String voucherType = inputConsole.getVoucherType();
        long value = inputConsole.getVoucherValue();

        voucherService.createVoucher(new VoucherForm(voucherType, value));
    }
}
