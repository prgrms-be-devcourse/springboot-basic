package org.prgrms.kdt.forward;

import org.prgrms.kdt.controller.VoucherController;
import org.prgrms.kdt.controller.request.CreateVoucherRequest;
import org.prgrms.kdt.forward.io.Input;
import org.prgrms.kdt.forward.io.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IOController {
    private final Input input;
    private final Output output;
    private final VoucherController voucherController;

    @Autowired
    public IOController(Input input, Output output, VoucherController voucherController) {
        this.input = input;
        this.output = output;
        this.voucherController = voucherController;
    }

    public void run() {
        boolean exit = false;
        output.write(voucherController.introduceCommand());

        do {
            try {
                String command = input.readLine();
                switch (command) {
                    case "create" -> {
                        output.write(voucherController.requestVoucherInfo());
                        String requestedVoucherInfo = input.readLine();
                        CreateVoucherRequest createVoucherRequest = new CreateVoucherRequest(requestedVoucherInfo);
                        output.write(voucherController.create(createVoucherRequest));
                    }
                    case "list" -> {
                        output.write(voucherController.list());
                    }
                    case "exit" -> {
                        output.write(voucherController.exit());
                        exit = true;
                    }
                    default -> {
                        output.write(voucherController.wrongCommand());
                    }
                }
            } catch (RuntimeException e) {
                output.write("> " + e.getMessage());
            }

            if (!exit) output.write(voucherController.introduceCommand());
        } while (!exit);

        input.close();
    }
}
