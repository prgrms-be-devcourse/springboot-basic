package prgms.spring_week1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import prgms.spring_week1.domain.customer.model.Customer;
import prgms.spring_week1.domain.customer.model.embeddedType.Email;
import prgms.spring_week1.domain.customer.service.CustomerService;
import prgms.spring_week1.domain.voucher.model.Voucher;
import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.service.VoucherService;
import prgms.spring_week1.exception.NoSuchVoucherTypeException;
import prgms.spring_week1.io.Input;
import prgms.spring_week1.io.Output;
import prgms.spring_week1.io.message.ConsoleOutputMessage;
import prgms.spring_week1.menu.CustomerMenu;
import prgms.spring_week1.menu.Menu;
import prgms.spring_week1.menu.VoucherMenu;

import java.util.Collections;
import java.util.List;

@Component
public class CommandLine implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(CommandLine.class);

    private final Input input = new Input();
    private final Output output = new Output();
    private final VoucherService voucherService;
    private final CustomerService customerService;

    private CommandLine(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean isRunning = true;

        while (isRunning) {
            Menu menuName = input.getMenu();
            switch (menuName) {
                case EXIT -> isRunning = false;
                case VOUCHER -> selectVoucherMenu();
                case CUSTOMER -> selectCustomerMenu();
            }
        }
    }

    private void selectVoucherMenu() {
        VoucherMenu menuName = input.getVoucherMenu();
        switch (menuName) {
            case INSERT -> insertVoucher();
            case FIND_ALL -> output.printAllVoucher(voucherService.findAll());
            case FIND_BY_TYPE -> output.printAllVoucher(getVoucherListByType());
            case DELETE_ALL -> voucherService.deleteAll();
        }
    }

    private void insertVoucher() {
        try {
            processVoucherInsert();
        } catch (RuntimeException e) {
            logger.warn(e.getMessage());
            input.printConsoleMessage(ConsoleOutputMessage.INVALID_INPUT_DISCOUNT_MESSAGE);
        }
    }

    private void processVoucherInsert() {
        VoucherType voucherType = input.selectVoucherType();
        int discountValue = input.insertDiscountValue();
        voucherService.insertNewVoucher(voucherType, discountValue);
        input.printConsoleMessage(ConsoleOutputMessage.COMPLETE_VOUCHER_INSERT_MESSAGE);
    }

    private List<Voucher> getVoucherListByType() {
        try {
            return voucherService.findByType(input.inputVoucherType());
        } catch (NoSuchVoucherTypeException e) {
            logger.warn(e.getMessage());
            input.printConsoleMessage(ConsoleOutputMessage.INVALID_INPUT_DISCOUNT_MESSAGE);
            return Collections.emptyList();
        }
    }

    private void selectCustomerMenu() {
        CustomerMenu menuName = input.getCustomerMenu();

        switch (menuName) {
            case INSERT -> insertCustomer();
            case FIND_ALL -> printAllCustomer(customerService.findAll());
            case FIND_BY_EMAIL -> getCustomerByEmail();
            case BLACK -> customerService.getBlackConsumerList();
            case UPDATE_INFO -> updateCustomerInfo();
            case DELETE_BY_EMAIL -> customerService.deleteByEmail(input.inputEmail());
            case DELETE_ALL -> customerService.deleteAll();
        }
    }

    private void insertCustomer() {
        String name = input.inputName();
        Email email = input.inputEmail();

        customerService.insert(name, email);
    }

    private void printAllCustomer(List<Customer> customerList) {
        for (Customer customer : customerList) {
            printCustomerInfo(customer);
        }
    }

    private void printCustomerInfo(Customer customer) {
        output.printCustomerInfo(customer.getName(), customer.getEmail());
    }

    private void getCustomerByEmail() {
        Customer customer = customerService.findByEmail(input.inputEmail());

        if (customer == null) {
            return;
        }

        printCustomerInfo(customer);
    }

    private void updateCustomerInfo() {
        customerService.updateInfo(input.inputUpdateEmailInfo());
    }
}
