package com.prgms.VoucherApp.domain.customer.controller.console;

import com.prgms.VoucherApp.domain.customer.dto.CustomerCreateRequest;
import com.prgms.VoucherApp.domain.customer.dto.CustomerUpdateRequest;
import com.prgms.VoucherApp.domain.customer.dto.CustomersResponse;
import com.prgms.VoucherApp.domain.customer.model.CustomerService;
import com.prgms.VoucherApp.domain.customer.model.CustomerStatus;
import com.prgms.VoucherApp.view.CustomerCommand;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class CustomerManagementController implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(CustomerManagementController.class);
    private final CustomerService customerService;
    private final Input input;
    private final Output output;

    public CustomerManagementController(CustomerService customerService, Input input, Output output) {
        this.customerService = customerService;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        boolean isRunning = true;
        while (isRunning) {
            try {
                output.printCustomerCommand();
                Integer inputCustomerNumber = input.inputCustomerCommand();
                CustomerCommand customerCommand = CustomerCommand.findByCustomerTypeNumber(inputCustomerNumber);

                switch (customerCommand) {
                    case CREATE -> {
                        String inputCustomerStatus = input.inputCustomerStatus();
                        CustomerStatus inputStatus = CustomerStatus.findByStatus(inputCustomerStatus);

                        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(inputStatus);

                        customerService.save(customerCreateRequest);
                    }

                    case FIND_ALL -> {
                        CustomersResponse findCustomers = customerService.findAll();
                        output.printCustomers(findCustomers);
                    }

                    case FIND_ONE -> {
                        String inputUUID = input.inputUUID();
                        UUID customerId = UUID.fromString(inputUUID);

                        customerService.findOne(customerId)
                                .ifPresentOrElse(output::printCustomer, output::printFindEmpty);
                    }

                    case FIND_BY_STATUS -> {
                        String inputCustomerStatus = input.inputCustomerStatus();
                        CustomerStatus customerStatus = CustomerStatus.findByStatus(inputCustomerStatus);

                        CustomerCreateRequest customerCreateRequest = new CustomerCreateRequest(customerStatus);

                        CustomersResponse findCustomers = customerService.findByStatus(customerCreateRequest);

                        output.printCustomers(findCustomers);
                    }

                    case FIND_BLACKLIST -> {
                        CustomersResponse blackLists = customerService.readBlackLists();
                        output.printBlackLists(blackLists);
                    }

                    case UPDATE -> {
                        String inputUUID = input.inputUUID();
                        UUID customerId = UUID.fromString(inputUUID);

                        String inputCustomerStatus = input.inputCustomerStatus();
                        CustomerStatus customerStatus = CustomerStatus.findByStatus(inputCustomerStatus);

                        CustomerUpdateRequest customerUpdateRequest = new CustomerUpdateRequest(customerId, customerStatus);

                        customerService.update(customerUpdateRequest);
                    }

                    case DELETE -> {
                        String inputUUID = input.inputUUID();
                        UUID customerId = UUID.fromString(inputUUID);

                        customerService.deleteById(customerId);
                    }

                    case EXIT -> {
                        isRunning = false;
                    }

                    default -> {
                        output.printNotImplementMsg();
                    }
                }

            } catch (RuntimeException exception) {
                logger.debug("고객 관리 프로그램 실행 중 발생한 예외를 처리하였습니다.", exception);
                output.printErrorMsg(exception.getMessage());
            }
        }
    }
}
