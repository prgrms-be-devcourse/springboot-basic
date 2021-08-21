package com.example.kdtspringmission;

import com.example.kdtspringmission.view.InputView;
import com.example.kdtspringmission.view.OutputView;
import com.example.kdtspringmission.voucher.domain.VoucherFactory;
import com.example.kdtspringmission.voucher.repository.VoucherRepository;

public class CommandLineApplication {

    private final InputView inputView;
    private final OutputView outputView;
    private final VoucherRepository voucherRepository;

    public CommandLineApplication(InputView inputView, OutputView outputView,
        VoucherRepository voucherRepository) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.voucherRepository = voucherRepository;
    }

    public static void main(String[] args) {
        AppConfig ac = new AppConfig();
        new CommandLineApplication(ac.inputView(), ac.outputView(), ac.voucherRepository()).run();
    }

    public void run() {
        while (true) {

            outputView.commandList();
            String command = inputView.getCommand();

            if (command.equals("exit")) {
                break;
            }

            if (command.equals("create")) {
                outputView.creatableVoucherList();
                voucherRepository.insert(VoucherFactory.create(inputView.nextLine()));
            }

            if (command.equals("list")) {
                outputView.voucherList(voucherRepository.findAll());
            }
        }
    }

}
