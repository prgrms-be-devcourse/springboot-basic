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
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import java.util.List;

public class Console<T extends TextTerminal>  implements Input, Output {
    private TextIO textIO;
    private T terminal;
    private final FixedDiscountVoucherController fixedDiscountVoucherController;
    private final PercentDiscountVoucherController percentDiscountVoucherController;
    private final VoucherService voucherService;

    public Console(TextIO textIO, T terminal, VoucherRepository voucherRepository) {
        this.textIO = textIO;
        this.terminal = terminal;
        this.voucherService= new VoucherService(voucherRepository);
        this.fixedDiscountVoucherController = new FixedDiscountVoucherController(this, this, voucherService);
        this.percentDiscountVoucherController = new PercentDiscountVoucherController(this, this, voucherService);
    }

    @Override
    public String readCommand(String message) {
        return textIO.newStringInputReader().read(message);
    }

    @Override
    public String readString(String message) {
        return textIO.newStringInputReader().read(message);
    }

    @Override
    public void println(String message) {
        terminal.println(message);
    }

    public void run(){
        consoleMenu();
        while (true){
            String inputCommand = readCommand("명령어를 입력해주세요.(exit, create, list)");
            switch (inputCommand) {
                case "exit":
                    println("프로그램을 종료합니다.");
                    return;
                case "create":
                    create();
                    break;
                case "list":
                    list();
                    break;
                default:
                    println("허용된 명령어가 아닙니다. exit(종료), create(바우처 생성), list(바우처 목록)중 하나를 선택하세요. ");
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
                println("입력가능한 Voucher Type은 고정 금액 할인과 퍼센트 금액 할인 타입 입니다.");
                println("FixedAmountVoucher과 PercentDiscountVoucher 중 하나를 선택하여 생성해주세요.");
                break;
        }
    }

    private void list() {
        try {
            List<Voucher> vouchers = voucherService.fetchAllVouchers();

            if (vouchers.isEmpty()) {
                println("생성된 Voucher가 없습니다.");
            } else {
                println("생성된 Voucher 목록입니다.");
                for (Voucher voucher : vouchers) {
                    if (voucher instanceof FixedDiscountVoucher) {
                        println("FixedAmountVoucher: " + voucher.getDiscount());
                    } else if (voucher instanceof PercentDiscountVoucher) {
                        println("PercentDiscountVoucher: " + voucher.getDiscount());
                    }
                }
            }
        } catch (Exception e) {
            println("Voucher 목록을 가져오는 중 오류가 발생했습니다.");
        }
    }

    public void consoleMenu(){
        terminal.println("=== Voucher Program ===");
        terminal.println("Type exit to exit the program.");
        terminal.println("Type create to create a new voucher.");
        terminal.println("Type list to list all vouchers.");
    }
}
