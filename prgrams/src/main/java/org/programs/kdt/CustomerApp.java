package org.programs.kdt;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Command.CustomerMenu;
import org.programs.kdt.Customer.Customer;
import org.programs.kdt.Customer.CustomerService;
import org.programs.kdt.IO.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.programs.kdt.Exception.ErrorCode.*;

@RequiredArgsConstructor
@Component
public class CustomerApp {

    private static final Logger logger = LoggerFactory.getLogger(CustomerMenu.class);
    private final Console console;
    private final CustomerService customerService;

    void customerProcess() {

        CustomerMenu customerMenu =  CustomerMenu.ERROR;
        while(!customerMenu.equals(CustomerMenu.RETURN)) {
            console.output(CustomerMenu.toMenu());
            String input = console.input("명령어를 입력해주세요");
            customerMenu = CustomerMenu.find(input);
            customerMenuProcess(customerMenu);
        }
    }

    private void customerMenuProcess(CustomerMenu customerMenu) {
        switch (customerMenu) {
            case CREATE:
                customerCreateProcess();
                break;
            case FIND_ALL:
                customerFindAllProcess();
                break;
            case ERROR:
                logger.error(INVALID_COMMAND_TYPE.getMessage());
                break;
            case DELETE:
                customerDeleteProcess();
                break;
            case UPDATE:
                customerUpdateProcess();
                break;
            case BLACKLIST:
                customerSetBlacklistProcess();
            case FIND_BLACKLIST:
                customerBlacklistProcess();
                break;
            case FIND_EMAIL:
                customerFindEmailProcess();
                break;
            case RETURN:
                break;
            default:
                logger.error(INVALID_ENUM_TYPE.getMessage());
        }
    }

    private void customerSetBlacklistProcess() {
        String email = console.input("블랙리스트를 등록할 이메일을 입력해주세요.");
        Optional<Customer> findCustomer = customerService.findByEmail(email);
        if (findCustomer.isPresent()) {
            Customer customer = findCustomer.get();
            customer.setBlacklist();
            customerService.update(customer);
        } else {
            console.output(NOT_FOUND_EMAIL.getMessage());
        }
    }

    private void customerFindEmailProcess() {
        String email = console.input("이메일을 입력해주세요.");
        Optional<Customer> findCustomer = customerService.findByEmail(email);
        if (findCustomer.isPresent()) {
            Customer customer = findCustomer.get();
            console.printCustomer(customer);
        } else {
            console.output(NOT_FOUND_EMAIL.getMessage());
        }
    }

    private void customerBlacklistProcess() {
        List<Customer> blackList = customerService.findAllBlackList();
        console.printCustomerList(blackList);
    }

    private void customerUpdateProcess() {
        String email = console.input("찾을 유저의 이메일을 입력해주세요");
        Optional<Customer> findCustomer = customerService.findByEmail(email);
        if (findCustomer.isPresent()) {
            Customer customer = findCustomer.get();
            String name = console.input("변경할 이름을 입력해주세요");
            customer.changeName(name);
            customerService.update(customer);
        } else {
            console.output(NOT_FOUND_EMAIL.getMessage());
        }

    }

    private void customerDeleteProcess() {
        String email = console.input("삭제하실 이메일을 입력해주세요.");
        Optional<Customer> customer = customerService.findByEmail(email);
        if (customer.isPresent()) {
            customerService.deleteByEmail(email);
        } else {
            console.output(NOT_FOUND_EMAIL.getMessage());
        }
    }

    private void customerFindAllProcess() {
        List<Customer> customerList = customerService.findAll();
        console.printCustomerList(customerList);
    }

    private void customerCreateProcess() {
        try {
        customerService.save(inputCustomer());
        console.output("저장 되었습니다.");
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
    }

    private Customer inputCustomer() {
        String name = console.input("이름을 입력해주세요");
        String email = console.input("이메일을 입력해주세요");
        return new Customer(UUID.randomUUID(), name, email);
    }
}
