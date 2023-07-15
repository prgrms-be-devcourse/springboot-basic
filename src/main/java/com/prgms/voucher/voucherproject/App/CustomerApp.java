package com.prgms.voucher.voucherproject.App;

import com.prgms.voucher.voucherproject.domain.customer.Customer;
import com.prgms.voucher.voucherproject.domain.customer.CustomerType;
import com.prgms.voucher.voucherproject.exception.DuplicateCustomerException;
import com.prgms.voucher.voucherproject.io.Console;
import com.prgms.voucher.voucherproject.io.Constant;
import com.prgms.voucher.voucherproject.service.CustomerService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerApp {

    private final Console console = new Console();
    private final CustomerService customerService;

    public CustomerApp(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void customerRun() throws Exception {
        boolean isRunning = true;

        while (isRunning) {
            CustomerType customerApp = console.inputCustomerMenu();

            if (customerApp == null) continue;

            switch (customerApp) {
                case CREATE -> createCustomer();
                case LIST -> listCustomers();
                case FIND -> System.out.println("find");
                case DELETE -> System.out.println("delete");
                case EXIT -> {
                    isRunning = false;
                    console.printMessage(Constant.PROGRAM_END, true);
                }
            }
        }
    }

    private void createCustomer() {
        Customer customer = null;
        // console에서 email, name 입력 받아서
        // email중복일때 try - catch로 예외 처리 필요
        try {
            customer = console.inputCreateCustomer();
        } catch (DuplicateCustomerException e) {
            console.printMessage(e.getLocalizedMessage(), true);
        }

        if (customer == null) return;

        customerService.create(customer);
    }

    private void listCustomers() {
        List<Customer> customers = customerService.list();

        if (customers.isEmpty()) {
            console.printMessage(Constant.NOT_EXITS_CUSTOMER, true);
        }

        for (Customer c : customers) {
            console.printCustomerInfo(c);
        }
    }

    private void findCustomer() {
        // console에서 email 입력 받아서
        // customerService의 findByEmail 인자로 넘겨주기
        // Optionl이 empty라면 존재하지 않는 사용자라고 출력하고
        // empty가 아니라면 value 꺼내와서 출력하기
        // 출력하는 것도 list에서 사용했던 customer 사용자 정보 출력 메소드 그대로 사용
    }

    private void deleteCustomer() {
        // console에서 email 입력받아서 => findCustomer랑 같이 쓰자
        // customerService의 deleteByEmail 인자로 넘겨주기
    }

}
