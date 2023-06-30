package com.prgrms.springbootbasic;

import com.prgrms.springbootbasic.controller.FixedDiscountVoucherController;
import com.prgrms.springbootbasic.controller.PercentDiscountVoucherController;
import com.prgrms.springbootbasic.domain.FixedDiscountVoucher;
import com.prgrms.springbootbasic.domain.PercentDiscountVoucher;
import com.prgrms.springbootbasic.domain.Voucher;
import com.prgrms.springbootbasic.io.Input;
import com.prgrms.springbootbasic.io.Output;
import com.prgrms.springbootbasic.repository.VoucherRepository;
import com.prgrms.springbootbasic.service.VoucherService;
import java.io.Console;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.beryx.textio.TextIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

public class ConsoleApplication implements Input, Output, CommandLineRunner {

    private TextIO textIO;
    private T terminal;
    private final FixedDiscountVoucherController fixedDiscountVoucherController;
    private final PercentDiscountVoucherController percentDiscountVoucherController;
    private final VoucherService voucherService;
    public static final Logger logger = LoggerFactory.getLogger(Console.class);

    public Console(TextIO textIO, T terminal, VoucherRepository voucherRepository) {
        this.textIO = textIO;
        this.terminal = terminal;
        this.voucherService = new VoucherService(voucherRepository);
        this.fixedDiscountVoucherController = new FixedDiscountVoucherController(this, this, voucherService);
        this.percentDiscountVoucherController = new PercentDiscountVoucherController(this, this, voucherService);
    }

    @Override
    public String readCommand(String message) {
        return textIO.newStringInputReader().read(message);
    }

    @Override
    public String readVoucherType(String message) {
        return null;
    }

    @Override
    public String readVoucherValue(String message) {
        return null;
    }

    @Override
    public String readString(String message) {
        return textIO.newStringInputReader().read(message);
    }

    @Override
    public void printlnCommand(String message) {
        terminal.println(message);
    }

    @Override
    public void printCreateVoucherType() {

    }

    @Override
    public void printlnVoucherList(Map<UUID, Voucher> voucherMap) {

    }

    @Override
    public void errorMessage(String errorMessage) {

    }

    public void run() {
        consoleMenu();
        while (true) {
            String inputCommand = readCommand("명령어를 입력해주세요.(exit, create, list)");
            switch (inputCommand) {
                case "exit":
                    printlnCommand("프로그램을 종료합니다.");
                    return;
                case "create":
                    create();
                    break;
                case "list":
                    list();
                    break;
                default:
                    printlnCommand("허용된 명령어가 아닙니다. exit(종료), create(바우처 생성), list(바우처 목록)중 하나를 선택하세요.");
                    logger.error("명령어 입력 오류");
                    break;
            }
        }
    }

    private void create() {
        String voucherType = readString("생성할 voucher의 종류를 입력해주세요.(FixedAmountVoucher, PercentDiscountVoucher)");
        switch (voucherType) {
            case "FixedAmountVoucher":
                fixedDiscountVoucherController.createFixedDiscountVoucher();
                break;
            case "PercentDiscountVoucher":
                percentDiscountVoucherController.createPercentDiscountVoucher();
                break;
            default:
                printlnCommand("입력가능한 Voucher Type은 고정 금액 할인과 퍼센트 금액 할인 타입 입니다.");
                printlnCommand("FixedAmountVoucher과 PercentDiscountVoucher 중 하나를 선택하여 생성해주세요.");
                logger.error("Voucher Type입력 오류");
                break;
        }
    }

    private void list() {
        try {
            List<Voucher> vouchers = voucherService.fetchAllVouchers();

            if (vouchers.isEmpty()) {
                printlnCommand("생성된 Voucher가 없습니다.");
            } else {
                printlnCommand("생성된 Voucher 목록입니다.");
                for (Voucher voucher : vouchers) {
                    if (voucher instanceof FixedDiscountVoucher) {
                        printlnCommand("FixedAmountVoucher: " + voucher.getDiscount());
                    } else if (voucher instanceof PercentDiscountVoucher) {
                        printlnCommand("PercentDiscountVoucher: " + voucher.getDiscount());
                    }
                }
            }
        } catch (Exception e) {
            printlnCommand("Voucher 목록을 가져오는 중 오류가 발생했습니다.");
            logger.error("Voucher 목록 불러오기 오류");
        }
    }

    public void consoleMenu() {
        terminal.println("=== Voucher Program ===");
        terminal.println("Type exit to exit the program.");
        terminal.println("Type create to create a new voucher.");
        terminal.println("Type list to list all vouchers.");
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
