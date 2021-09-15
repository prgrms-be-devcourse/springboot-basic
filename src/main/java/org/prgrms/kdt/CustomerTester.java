package org.prgrms.kdt;

import org.prgrms.kdt.Exception.InputFormatException;
import org.prgrms.kdt.customer.CustomerRepository;
import org.prgrms.kdt.customer.CustomerService;
import org.prgrms.kdt.IO.Input;
import org.prgrms.kdt.IO.Output;
import org.prgrms.kdt.util.CustomerTesterGuideComment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class CustomerTester {

    private boolean exitProgram = false;

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    public CustomerTester(CustomerService customerService, CustomerRepository customerRepository){
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    public void run() {
        Input input = new Console();
        Output output = new Console();

        while (!exitProgram) {
            try {
                TesterCommandType commandType = TesterCommandType.matchCommandType(input.input(CustomerTesterGuideComment.commandGuide));
                switch (commandType) {
                    case EXIT:
                        output.print(CustomerTesterGuideComment.exitMsg);
                        exitProgram = true;
                        break;

                    case CREATE:
                        customerService.createCustomer(UUID.randomUUID(), input.input("고객 이름"), input.input("이메일"), LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), null);
                        break;

                    case LIST:
                        output.print("고객은 총 " + customerRepository.count() + "명 입니다.");
                        output.print(customerRepository.getList());
                        break;
                }
            } catch (InputFormatException inputCommandException){
                output.print(CustomerTesterGuideComment.inputErrorMsg);
            }
            output.print(CustomerTesterGuideComment.endLine);
        }

    }

}

