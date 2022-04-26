package org.prgrms.springbasic.service.console;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.domain.customer.CustomerType;
import org.prgrms.springbasic.domain.voucher.VoucherType;
import org.prgrms.springbasic.repository.customer.CustomerRepository;
import org.prgrms.springbasic.repository.voucher.VoucherRepository;
import org.prgrms.springbasic.utils.io.console.Console;
import org.springframework.stereotype.Service;

import static java.util.UUID.randomUUID;
import static org.prgrms.springbasic.domain.customer.Customer.blackCustomer;
import static org.prgrms.springbasic.domain.customer.Customer.normalCustomer;
import static org.prgrms.springbasic.domain.voucher.Voucher.fixedVoucher;
import static org.prgrms.springbasic.domain.voucher.Voucher.percentVoucher;
import static org.prgrms.springbasic.utils.enumm.message.CustomerConsole.CUSTOMER_COMMAND_LIST;
import static org.prgrms.springbasic.utils.enumm.message.CustomerConsole.INPUT_CUSTOMER_NAME;
import static org.prgrms.springbasic.utils.enumm.message.VoucherConsole.TYPE_DISCOUNT_INFO;
import static org.prgrms.springbasic.utils.enumm.message.VoucherConsole.VOUCHER_COMMAND_LIST;
import static org.prgrms.springbasic.utils.validator.CustomerValidator.validateCustomerType;
import static org.prgrms.springbasic.utils.validator.CustomerValidator.validateCustomers;
import static org.prgrms.springbasic.utils.validator.VoucherValidator.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsoleServiceImpl implements ConsoleService {

    private final CustomerRepository customerRepository;
    private final VoucherRepository voucherRepository;
    private final Console console;

    @Override
    public void register() {
        console.printToConsole(CUSTOMER_COMMAND_LIST.getMessage());

        var customerType = validateCustomerType(console.takeInput());

        console.printToConsole(INPUT_CUSTOMER_NAME.getMessage());

        String name = console.takeInput();

        registerCustomer(customerType, name);
    }

    @Override
    public void customers() {
        var customers = validateCustomers(customerRepository.findCustomers());

        customers.forEach(console::printToConsole);
    }

    @Override
    public void create() {
        console.printToConsole(VOUCHER_COMMAND_LIST.getMessage());

        var voucherType = validateVoucherType(console.takeInput());

        console.printToConsole(TYPE_DISCOUNT_INFO.getMessage());

        var discountInfo = validateDiscountInfo(console.takeInput());

        createVoucher(voucherType, discountInfo);
    }

    @Override
    public void vouchers() {
        var vouchers = validateVouchers(voucherRepository.findVouchers());

        vouchers.forEach(console::printToConsole);
    }

    @Override
    public void exit() {
        log.info("program exit");
        voucherRepository.deleteVouchers();
        customerRepository.deleteCustomers();
        System.exit(0);
    }

    private void registerCustomer(CustomerType customerType, String name) {
        switch(customerType) {
            case NORMAL:
                customerRepository.save(normalCustomer(randomUUID(), name));
                break;
            case BLACK:
                customerRepository.save(blackCustomer(randomUUID(), name));
                break;
            default:
                break;
        }
    }

    private void createVoucher(VoucherType voucherType, long discountInfo) {
        switch(voucherType) {
            case FIXED:
                voucherRepository.save(fixedVoucher(randomUUID(), discountInfo));
                break;
            case PERCENT:
                voucherRepository.save(percentVoucher(randomUUID(), discountInfo));
                break;
            default:
                break;
        }
    }
}
