package org.programmers.kdt.weekly;

import org.programmers.kdt.weekly.customer.repository.CustomerRepository;
import org.programmers.kdt.weekly.io.Input;
import org.programmers.kdt.weekly.io.InputErrorType;
import org.programmers.kdt.weekly.io.Output;
import org.programmers.kdt.weekly.voucher.repository.VoucherRepository;
import org.programmers.kdt.weekly.voucher.service.VoucherCreateService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommandLineApplication {
    final String CREATE = "create";
    final String LIST = "list";
    final String EXIT = "exit";
    final String BLACK_LIST = "list -b";

    private Input input;
    private Output output;
    private VoucherCreateService voucherCreateService;
    private VoucherRepository voucherRepository;
    private CustomerRepository customerRepository;

    public CommandLineApplication(Input input, Output output, VoucherCreateService voucherCreateService, VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.input = input;
        this.output = output;
        this.voucherCreateService = voucherCreateService;
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    public void start() {
        var programExit = false;

        while (!programExit) {
            output.startMessage();
            Optional<String> command = Optional.ofNullable(input.getUserInput());
            if (command.isEmpty()) {
                output.inputErrorMessage(InputErrorType.INVALID);
            } else {
                switch (command.get().toLowerCase()) {
                    case CREATE :
                        voucherCreateService.create();
                        break;
                    case LIST:
                        if (voucherRepository.getSize() < 1) {
                            output.inputErrorMessage(InputErrorType.VOUCHER_EMPTY);
                        } else {
                            voucherRepository.showAll();
                        }
                        break;

                    case BLACK_LIST:
                        if (customerRepository.getSize() > 1) {
                            customerRepository.showAll();
                        } else {
                            output.inputErrorMessage(InputErrorType.BLACK_LIST_EMPTY);
                        }
                        break;

                    case EXIT:
                        System.out.println(EXIT);
                        programExit = true;
                        break;

                    default:
                        output.inputErrorMessage(InputErrorType.COMMAND);
                        break;
                }
            }
            System.out.println();
        }
    }

}
