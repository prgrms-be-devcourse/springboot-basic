package kr.co.programmers.springbootbasic;


import kr.co.programmers.springbootbasic.dto.VoucherRequestDto;
import kr.co.programmers.springbootbasic.dto.VoucherResponseDto;
import kr.co.programmers.springbootbasic.io.Input;
import kr.co.programmers.springbootbasic.io.MenuCommand;
import kr.co.programmers.springbootbasic.io.Output;
import kr.co.programmers.springbootbasic.util.VoucherUtils;
import kr.co.programmers.springbootbasic.voucher.VoucherService;
import kr.co.programmers.springbootbasic.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(Processor.class);
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
            executeServiceLoop();
        }
        logger.info("서비스를 종료합니다.");
        outputConsole.printExit();
    }

    private void executeServiceLoop() {
        try {
            doService();
        } catch (RuntimeException e) {
            outputConsole.printMessage(e.getMessage());
        }
    }

    private void doService() throws RuntimeException {
        outputConsole.printProgramMenu();
        MenuCommand menuCommand = inputConsole.readMenuCommand();
        switch (menuCommand) {
            case EXIT -> isExit = true;
            case CREATE -> createVoucher();
            case LIST -> listAllVoucher();
        }
    }

    private void createVoucher() throws RuntimeException {
        outputConsole.printCreationMenu();
        VoucherType type = inputConsole.readVoucherType();

        outputConsole.printAmountEnterMessage(type);
        long amount = inputConsole.readAmount();

        VoucherRequestDto requestDto = new VoucherRequestDto(type, amount);
        VoucherResponseDto responseDto = voucherService.createVoucher(requestDto);
        outputConsole.printMessage(VoucherUtils.formatVoucherResponseDto(responseDto));
    }

    private void listAllVoucher() {
        String message = voucherService.listAllVoucher();
        outputConsole.printMessage(message);
    }
}
