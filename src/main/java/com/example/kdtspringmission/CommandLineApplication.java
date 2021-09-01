package com.example.kdtspringmission;

import com.example.kdtspringmission.view.InputView;
import com.example.kdtspringmission.view.OutputView;
import com.example.kdtspringmission.voucher.repository.VoucherRepository;
import com.example.kdtspringmission.voucher.service.VoucherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class CommandLineApplication {

    private final InputView inputView;
    private final OutputView outputView;
    private final VoucherService voucherService;

    public CommandLineApplication(InputView inputView, OutputView outputView,
        VoucherService voucherService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.voucherService = voucherService;
    }

    public void run() {
        while (true) {
            System.out.println(voucherService.getVoucherRepository().getClass());
            outputView.commandList();
            executeCommand(inputView.getCommand());
        }
    }

    private void executeCommand(Command command) {
        if (command == Command.EXIT) {
            System.exit(0);
            return;
        }

        if (command == Command.CREATE) {
            outputView.creatableVoucherList();
            voucherService.createAndPersist(inputView.nextLine());
            return;
        }

        if (command == Command.LIST) {
            outputView.voucherList(voucherService.findAll());
        }

    }

}
