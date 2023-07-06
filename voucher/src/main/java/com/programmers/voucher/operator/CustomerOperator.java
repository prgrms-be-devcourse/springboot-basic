package com.programmers.voucher.operator;

import com.programmers.voucher.console.Console;
import com.programmers.voucher.console.Printer;
import com.programmers.voucher.domain.customer.Customer;
import com.programmers.voucher.domain.enums.CustomerOperation;
import com.programmers.voucher.stream.customer.CustomerStream;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomerOperator {

    private final Console console;
    private final Printer printer;
    private final CustomerStream customerStream;


    public CustomerOperator(Console console, Printer printer, CustomerStream customerStream) {
        this.console = console;
        this.printer = printer;
        this.customerStream = customerStream;
    }

    public void customerOperation() {
        String operationInput = console.getCustomerOperation();
        CustomerOperation customerOperation = CustomerOperation.convertStringToCustomerOperation(operationInput).orElseThrow(
            () -> new IllegalArgumentException("지원하지 않는 버전입니다. 버전을 다시 확인 해 주세요.")
        );
        switch (customerOperation) {
            case CREATE -> createCustomer();
            case FINDALL -> printCustomerList();
            case FINDBYID -> printCustomer();
            case UPDATE -> updateCustomer();
            case DELETEBYID -> deleteCustomer();
            case DELETEALL -> deleteAllCustomer();
        }

    }

    private void createCustomer() {
        Map<String, String> customerInformation = console.getCustomerInformation();
        Customer customer = Customer.createCustomer(customerInformation);
        customerStream.save(customer);
    }

    private void printCustomerList() {
        printer.printListOfCustomer(customerStream.findAll());
    }

    private void printCustomer() {
        String customerId = console.getCustomerId();
        Customer customer = customerStream.findById(customerId).orElseThrow(
            () -> new IllegalArgumentException("존재하지 않는 Customer Id 입니다.")
        );
        printer.printCustomer(customer);
    }

    private void updateCustomer() {
        String customerId = console.getCustomerId();
        String customerName = console.getCustomerName();
        customerStream.update(customerId, customerName);
    }


    private void deleteCustomer() {
        String customerId = console.getCustomerId();
        customerStream.deleteById(customerId);
    }

    private void deleteAllCustomer() {
        customerStream.deleteAll();
    }
}
