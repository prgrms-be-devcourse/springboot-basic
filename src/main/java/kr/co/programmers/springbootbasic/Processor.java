package kr.co.programmers.springbootbasic;


import kr.co.programmers.springbootbasic.io.Input;
import kr.co.programmers.springbootbasic.io.MenuCommand;
import kr.co.programmers.springbootbasic.io.Output;
import kr.co.programmers.springbootbasic.voucher.Voucher;
import kr.co.programmers.springbootbasic.voucher.VoucherService;
import kr.co.programmers.springbootbasic.voucher.VoucherType;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ApplicationRunner {
    private boolean isExit;
    private final Input inputConsole;
    private final Output outputConsole;
    private final VoucherService voucherService;

    public Processor(Input inputConsole, Output outputConsole, VoucherService voucherService) {
        this.inputConsole = inputConsole;
        this.outputConsole = outputConsole;
        this.voucherService = voucherService;
        this.isExit = false;
    }

    @Override
    public void run(ApplicationArguments args) {
        while (!isExit) {
            try {
                doService();
            } catch (RuntimeException e) {
                outputConsole.printMessage(e.getMessage());
            }
        }
        outputConsole.printExit();
    }

    private void doService() {
        outputConsole.printProgramMenu();
        MenuCommand menuCommand = inputConsole.readMenuCommand();
        switch (menuCommand) {
            case EXIT -> isExit = true;
            case CREATE -> createVoucher();
            case LIST -> listAllVoucher();
        }
    }

    private void createVoucher() {
        outputConsole.printCreationMenu();
        VoucherType type = inputConsole.readVoucherType();
        outputConsole.printAmountEnterMessage(type);
        int amount = inputConsole.readAmount();
        Voucher voucher = voucherService.createVoucher(type, amount);
        outputConsole.printMessage(voucher.toString());
    }

    private void listAllVoucher() {
        String message = voucherService.listAllVoucher();
        outputConsole.printMessage(message);
    }
}
