package org.prgrms.kdtspringdemo.domain.customer;

import org.prgrms.kdtspringdemo.domain.console.Input;
import org.prgrms.kdtspringdemo.domain.console.Output;
import org.prgrms.kdtspringdemo.domain.customer.service.CustomerFileService;
import org.prgrms.kdtspringdemo.domain.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.domain.customer.type.CustomerDMLType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final Output output;
    private final Input input;
    private final CustomerService customerService;
    private final CustomerFileService customerFileService;

    public CustomerController(Output output, Input input, CustomerService customerStorage, CustomerFileService customerFileService) {
        this.output = output;
        this.input = input;
        this.customerService = customerStorage;
        this.customerFileService = customerFileService;
    }

    // Customer 데이터 처리에서 DML 선택
    public void chooseDML() {
        System.out.println(output.chooseCustomerDMLMessage());
        CustomerDMLType customerDMLType = input.inputCustomerDMLType();

        switch (customerDMLType) {
            case INSERT -> customerFileService.registerCustomer();
            case COUNT -> System.out.println("All customers number : " + customerService.count());
            case FINDALL -> customerService.findAll();
            case UPDATE, FINDBYID, FINDBYNAME, FINDBYEMAIL -> {
                System.out.println("sorry we do not support this service");
            }
            case DELETEALL -> {
                customerService.deleteAll();
                System.out.println("Delete all customer data");
            }
        }
    }

    public void readBlackListCSV() {
        customerFileService.readBlackListCSV();
    }
}
