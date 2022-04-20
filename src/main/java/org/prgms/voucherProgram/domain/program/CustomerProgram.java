package org.prgms.voucherProgram.domain.program;

import org.prgms.voucherProgram.domain.customer.Email;
import org.prgms.voucherProgram.domain.menu.CustomerMenuType;
import org.prgms.voucherProgram.dto.CustomerRequest;
import org.prgms.voucherProgram.exception.CustomerIsNotExistsException;
import org.prgms.voucherProgram.exception.DuplicateEmailException;
import org.prgms.voucherProgram.exception.WrongEmailException;
import org.prgms.voucherProgram.exception.WrongFileException;
import org.prgms.voucherProgram.exception.WrongNameException;
import org.prgms.voucherProgram.service.CustomerService;
import org.prgms.voucherProgram.view.Console;
import org.prgms.voucherProgram.view.InputView;
import org.prgms.voucherProgram.view.OutputView;
import org.springframework.stereotype.Component;

@Component
public class CustomerProgram {
    private final CustomerService customerService;
    private final InputView inputView;
    private final OutputView outputView;

    public CustomerProgram(CustomerService customerService, Console console) {
        this.customerService = customerService;
        this.inputView = console;
        this.outputView = console;
    }

    public void run() {
        boolean isNotEndProgram = true;

        while (isNotEndProgram) {
            CustomerMenuType customerMenuType = inputMenu();
            switch (customerMenuType) {
                case EXIT -> isNotEndProgram = false;
                case CREATE -> createCustomer();
                case READ -> readCustomer();
                case UPDATE -> updateCustomer();
                case DELETE -> deleteCustomer();
            }
        }
    }

    private CustomerMenuType inputMenu() {
        while (true) {
            try {
                return CustomerMenuType.fromMainMenu(inputView.inputCustomerMenu());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void createCustomer() {
        CustomerRequest customerRequest = inputView.inputCustomerInformation();
        while (true) {
            try {
                outputView.printCustomer(customerService.save(customerRequest));
                return;
            } catch (WrongNameException e) {
                outputView.printError(e.getMessage());
                customerRequest.setName(inputView.inputCustomerName());
            } catch (WrongEmailException e) {
                outputView.printError(e.getMessage());
                customerRequest.setEmail(inputView.inputCustomerEmail());
            }
        }
    }

    private void readCustomer() {
        CustomerMenuType customerMenuType = inputSubMenu();
        switch (customerMenuType) {
            case ALL -> outputView.printCustomers(customerService.findCustomers());
            case JUST_ONE -> printCustomer();
            case BLACKLIST -> printBlackList();
        }
    }

    private CustomerMenuType inputSubMenu() {
        while (true) {
            try {
                return CustomerMenuType.fromSubMenu(inputView.inputCustomerSubMenu());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void printCustomer() {
        while (true) {
            try {
                Email email = new Email(inputView.inputCustomerEmail());
                outputView.printCustomer(customerService.findByEmail(email));
                return;
            } catch (WrongEmailException e) {
                outputView.printError(e.getMessage());
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
                return;
            }
        }
    }

    private void updateCustomer() {
        Email email = inputCustomerEmail();
        CustomerRequest customerRequest = inputView.inputCustomerInformation();
        while (true) {
            try {
                outputView.printCustomer(customerService.update(email, customerRequest));
                return;
            } catch (WrongNameException e) {
                outputView.printError(e.getMessage());
                customerRequest.setName(inputView.inputCustomerName());
            } catch (WrongEmailException | DuplicateEmailException e) {
                outputView.printError(e.getMessage());
                customerRequest.setEmail(inputView.inputCustomerEmail());
            } catch (CustomerIsNotExistsException e) {
                outputView.printError(e.getMessage());
                return;
            }
        }
    }

    private Email inputCustomerEmail() {
        while (true) {
            try {
                return new Email(inputView.inputCustomerEmail());
            } catch (WrongEmailException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private void deleteCustomer() {
        try {
            customerService.delete(inputCustomerEmail());
            outputView.printSuccess();
        } catch (CustomerIsNotExistsException e) {
            outputView.printError(e.getMessage());
        }
    }

    private void printBlackList() {
        try {
            outputView.printCustomers(customerService.findBlackList());
        } catch (WrongFileException e) {
            outputView.printError(e.getMessage());
            System.exit(0);
        }
    }
}
