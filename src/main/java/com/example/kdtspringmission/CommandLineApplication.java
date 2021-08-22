package com.example.kdtspringmission;

import com.example.kdtspringmission.view.InputView;
import com.example.kdtspringmission.view.OutputView;
import com.example.kdtspringmission.voucher.repository.VoucherRepository;
import com.example.kdtspringmission.voucher.service.VoucherService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        InputView inputView = ac.getBean("consoleInputView", InputView.class);
        OutputView outputView = ac.getBean("consoleOutputView", OutputView.class);
        VoucherService voucherService = ac.getBean("voucherService", VoucherService.class);
        new CommandLineApplication(inputView, outputView, voucherService).run();
    }

    public void run() {
        while (true) {
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
