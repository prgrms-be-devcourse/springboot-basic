package com.programmers.springbootbasic.consolestarter;

import com.programmers.springbootbasic.io.ConsoleInput;
import com.programmers.springbootbasic.io.ConsoleOutputFormatPrinter;
import com.programmers.springbootbasic.io.StandardInput;
import com.programmers.springbootbasic.io.StandardOutput;
import com.programmers.springbootbasic.domain.Customer;
import com.programmers.springbootbasic.domain.CustomerVoucherLog;
import com.programmers.springbootbasic.domain.Voucher;
import com.programmers.springbootbasic.service.CustomerService;
import com.programmers.springbootbasic.service.CustomerVoucherLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.springbootbasic.io.ConsoleInput.*;
import static com.programmers.springbootbasic.consolestarter.VoucherManager.*;
import static com.programmers.springbootbasic.io.ConsoleOutputFormatPrinter.DIVISION_LINE;
import static com.programmers.springbootbasic.validation.CustomerValidator.NON_EXISTING_CUSTOMER_ID_EXCEPTION_MESSAGE;

@Component
public class CustomerManager {

    private final CustomerService customerService;
    private final CustomerVoucherLogService customerVoucherLogService;

    private final StandardInput input = new ConsoleInput();
    private final StandardOutput output = new ConsoleOutputFormatPrinter();

    @Autowired
    public CustomerManager(CustomerService customerService, CustomerVoucherLogService customerVoucherLogService) {
        this.customerService = customerService;
        this.customerVoucherLogService = customerVoucherLogService;
    }

    public void serviceCustomerWork() {
        showCustomerMenu();

        int response = Integer.parseInt(input.read());
        switch (response) {
            case 1 -> createNewCustomer();
            case 2 -> allocateVoucherToCustomer();
            case 3 -> searchVouchersByCustomerId();
            case 4 -> retrieveCustomerById();
            case 5 -> retrieveAllCustomers();
            case 6 -> deleteCustomer();
            case 7 -> deleteAllCustomers();
            case 8 -> output.writeln("처음으로 돌아갑니다.");
            default -> output.writeln(INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE);
        }
    }

    public void createNewCustomer() {
        String customerId = input.inputCustomerId();
        String name = input.inputCustomerName();

        Customer newCustomer = customerService.createCustomer(customerId, name);

        output.printNewCustomer(newCustomer);
    }

    public void allocateVoucherToCustomer() {
        String voucherId = input.inputVoucherId();
        validateVoucherId(voucherId);

        String customerId = input.inputCustomerId();

        CustomerVoucherLog customerVoucherLog = customerVoucherLogService.createCustomerVoucherLog(customerId, UUID.fromString(voucherId));

        output.printAllocationAcknowledgement(customerVoucherLog);
    }

    public void searchVouchersByCustomerId() {
        String customerId = input.inputCustomerId();

        List<Voucher> vouchersOfCustomer = customerVoucherLogService.getVouchersOfCustomer(customerId);

        output.printVouchersOfCustomer(customerId, vouchersOfCustomer);
    }

    public void retrieveCustomerById() {
        Customer foundCustomer = retrieveCustomer();

        output.printFoundCustomer(foundCustomer);
    }

    public void retrieveAllCustomers() {
        List<Customer> allCustomers = customerService.getAllCustomers();

        output.printAllCustomers(allCustomers);
    }

    public void deleteCustomer() {
        Customer customerToBeDeleted = retrieveCustomer();

        customerService.deleteCustomerById(customerToBeDeleted.getCustomerId());

        output.printDeletedCustomer(customerToBeDeleted);
    }

    private Customer retrieveCustomer() {
        String customerId = input.inputCustomerId();

        Optional<Customer> foundCustomer = customerService.getCustomerById(customerId);

        return foundCustomer.orElseThrow(() -> new IllegalArgumentException(NON_EXISTING_CUSTOMER_ID_EXCEPTION_MESSAGE));
    }

    public void deleteAllCustomers() {
        String response = input.promptInput("정말로 모든 고객을 삭제하시겠습니까(y/n)?").toLowerCase();

        switch (response) {
            case "y" -> {
                customerService.deleteAllCustomers();
                output.writeln("모든 고객이 정상적으로 삭제되었습니다.");
            }
            case "n" -> output.writeln("실행이 취소되었습니다.");
            default -> output.writeln(INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE);
        }
    }

    private void showCustomerMenu() {
        output.writeln(DIVISION_LINE);
        output.writeln("1. 새로운 고객 생성");
        output.writeln("2. 고객에게 할인권 할당");
        output.writeln("3. 고객이 보유한 할인권 조회");
        output.writeln("4. 아이디로 고객 조회");
        output.writeln("5. 모든 고객 조회");
        output.writeln("6. 아이디로 고객 삭제");
        output.writeln("7. 모든 고객 삭제");
        output.writeln("8. 처음으로");
        output.writeln(DIVISION_LINE);
        output.write("==> ");
    }

}
