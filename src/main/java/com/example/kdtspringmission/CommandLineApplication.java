package com.example.kdtspringmission;

import com.example.kdtspringmission.view.InputView;
import com.example.kdtspringmission.view.OutputView;
import com.example.kdtspringmission.voucher.repository.VoucherRepository;
import com.example.kdtspringmission.voucher.service.VoucherService;

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
        AppConfig ac = new AppConfig();
        new CommandLineApplication(ac.inputView(), ac.outputView(), ac.voucherService()).run();
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
