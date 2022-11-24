package org.prgrms.kdt.forward;

import org.prgrms.kdt.controller.CustomerController;
import org.prgrms.kdt.controller.VoucherController;
import org.prgrms.kdt.controller.request.CreateCustomerRequest;
import org.prgrms.kdt.controller.request.CreateVoucherRequest;
import org.prgrms.kdt.controller.response.CustomerResponse;
import org.prgrms.kdt.controller.response.VoucherResponse;
import org.prgrms.kdt.forward.io.Input;
import org.prgrms.kdt.forward.io.Output;
import org.prgrms.kdt.view.ConsoleView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IOController {
    private final Input input;
    private final Output output;
    private final VoucherController voucherController;

    private final CustomerController customerController;
    private final ConsoleView consoleView;
    private static final Logger logger = LoggerFactory.getLogger(IOController.class);

    public IOController(Input input, Output output, VoucherController voucherController, CustomerController customerController, ConsoleView consoleView) {
        this.input = input;
        this.output = output;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.consoleView = consoleView;
    }

    public void run() {
        boolean exit = false;
        logger.info("Start : Voucher Manage Program");
        output.write(consoleView.introduceCommand());

        while (!exit) {
            try {
                String command = input.readLine();
                switch (command) {
                    case "create-customer" -> {
                        logger.info("Request <create customer>");
                        createCustomer();
                    }
                    case "get-customer" -> {
                        logger.info("Request <get customer>");
                        getCustomerByEmail();
                    }
                    case "create-voucher" -> {
                        logger.info("Request <create voucher>");
                        createVoucher();
                    }
                    case "list" -> {
                        logger.info("Request <list voucher>");
                        list();
                    }
                    case "exit" -> {
                        logger.info("Request <exit>");
                        output.write(consoleView.exit());
                        exit = true;
                    }
                    default -> {
                        output.write(consoleView.wrongCommand());
                    }
                }
            } catch (RuntimeException e) {
                logger.error(e.getMessage());
                output.write("**" + e.getMessage());
            }

            if (!exit) output.write(consoleView.introduceCommand());
        }

        input.close();
        logger.info("Finish : Voucher Manage Program");
    }

    private void createVoucher() {
        output.write(consoleView.requestVoucherInfo());
        String requestedVoucherInfo = input.readLine();
        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(requestedVoucherInfo);
        if (voucherController.createVoucher(createVoucherRequest)) {
            output.write(consoleView.saveVoucher());
        } else output.write(consoleView.saveVoucherError());
    }

    private void createCustomer() {
        output.write(consoleView.requestCustomerInfoToCreate());
        String requestCustomerInfoToCreate = input.readLine();
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest(requestCustomerInfoToCreate);
        if (customerController.createCustomer(createCustomerRequest)){
            output.write(consoleView.saveCustomer());
        }
        else output.write(consoleView.saveCustomerError());
    }

    private void getCustomerByEmail(){
        output.write(consoleView.requestCustomerInfoToGet());
        String requestCustomerInfoToGet = input.readLine();
        CustomerResponse customerResponse = customerController.getCustomerByEmail(requestCustomerInfoToGet);
        if (customerResponse.customer() != null){
            output.write(consoleView.getCustomer(customerResponse));
        }
        else output.write(consoleView.getCustomerError());
    }

    private void list() {
        List<VoucherResponse> list = voucherController.getAllVouchers();
        if (list.isEmpty()) {
            output.write(consoleView.emptyVoucherList());
        } else output.write(consoleView.listVoucher(list));
    }
}
