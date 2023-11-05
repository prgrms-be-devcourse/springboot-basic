package com.prgms.vouchermanager.contorller.customer;

import com.prgms.vouchermanager.domain.customer.Customer;
import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.dto.CreateCustomerDto;
import com.prgms.vouchermanager.dto.UpdateCustomerDto;
import com.prgms.vouchermanager.service.customer.CustomerService;
import com.prgms.vouchermanager.util.io.ConsoleInput;
import com.prgms.vouchermanager.util.io.ConsoleOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class CustomerController {

    private final CustomerService customerService;
    private final ConsoleInput consoleInput;

    private final ConsoleOutput consoleOutput;
    public CustomerController(CustomerService customerService, ConsoleInput consoleInput, ConsoleOutput consoleOutput) {
        this.customerService = customerService;
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
    }

    public void create() {
        Customer customer = null;
        try {

            consoleOutput.printCustomerName();
            String name = consoleInput.inputCustomerName();
            consoleOutput.printCustomerEmail();
            String email = consoleInput.inputCustomerEmail();
            consoleOutput.printBlackList();
            int blackList = consoleInput.inputBlackList();
            customer = customerService.create(new CreateCustomerDto(name,email, blackList));
            log.info(customer.toString());

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            log.warn(e.getMessage());
        }

    }

    public void update() {
        try {
            consoleOutput.printCustomerId();
            Long id = consoleInput.inputCustomerId();
            consoleOutput.printCustomerName();
            String name = consoleInput.inputCustomerName();
            consoleOutput.printCustomerEmail();
            String email = consoleInput.inputCustomerEmail();
            consoleOutput.printBlackList();
            int blackList = consoleInput.inputBlackList();
            customerService.update(id,new UpdateCustomerDto(name,email,blackList));
            consoleOutput.printSuccessUpdate();

        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }
    }

    public void getList() {

        List<Customer> customers = customerService.findAll();

        customers.forEach(consoleOutput::printCustomer);

    }

    public void findById() {
        consoleOutput.printCustomerId();

        try {
            Long id = consoleInput.inputCustomerId();
            Customer customer = customerService.findById(id);
            log.info(customer.toString());
        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }

    }

    public void deleteById() {
        consoleOutput.printCustomerId();
        try {
            long id = consoleInput.inputCustomerId();
            customerService.deleteById(id);
            consoleOutput.printSuccessDelete();

        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }

    }

    public void deleteAll() {

        customerService.deleteAll();
        consoleOutput.printSuccessDelete();
    }
    public void findBlackList() {
        List<Customer> blackList = customerService.findBlackList();

        blackList.forEach(customer -> System.out.println(customer.toString()));
    }
}
