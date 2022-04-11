package org.prgrms.vouchermanager.shell;

import org.prgrms.vouchermanager.console.Console;
import org.prgrms.vouchermanager.voucher.domain.VoucherType;
import org.prgrms.vouchermanager.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;


//TODO: SpringbootShell 알아보고 적용해보기
public class CommandLineApplication {

    // 아직 사용하지 않는 로거
    private final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);

    private final Console console;
    private final VoucherService voucherService;
    private boolean initialized = false;

    public CommandLineApplication(Console console, VoucherService voucherService) {
        this.console = console;
        this.voucherService = voucherService;
    }

    public void run(String... args) {

        init();

        while (true) {
            logger.error("로깅 에러메세지 테스트 ");
            console.print("커맨드를 입력하세요: ");
            switch (getCommand()) {
                case EXIT -> {
                    return;
                }
                case CREATE -> {
                    createVoucher();
                }
                case LIST -> {
                    printVoucherLists();
                }
                case INVALID_COMMAND -> {
                    console.println("잘못된 커맨드, 다시 입력받기");
                }
            }
        }

    }

    private Command getCommand() {
        return Command.findCommand(console.read());
    }

    private void init() {
        if (initialized) return;
        initialized = true;
        console.print("""
                === Voucher Program ===
                Type exit to exit the program.
                Type create to create a new voucher.
                Type list to list all vouchers.
                """);
    }

    private void createVoucher() {
        VoucherType voucherType = getInputVoucherType();
        Long discountAmount = readDiscountAmount();
        voucherService.createVoucher(voucherType, discountAmount);
    }

    private VoucherType getInputVoucherType() {
        while (true) {
            printAvailableVoucherType();
            console.print("voucher type 입력 : ");
            VoucherType voucherType = VoucherType.findVoucherType(console.read());
            if (voucherType == VoucherType.INVALID) continue;
            return voucherType;
        }
    }

    private Long readDiscountAmount() {
        while (true) {
            console.println("1~100 사이의 할인율을 입력하세요.");
            Long amount = console.readLong();
            if (amount <= 0 || amount > 100) continue;
            return amount;
        }
    }

    private void printAvailableVoucherType() {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(VoucherType.values()).filter(v -> v != VoucherType.INVALID).forEach(v -> sb.append(v).append(", "));
        console.println(" 현재 가능한 타입 : " + sb);
    }

    private void printVoucherLists() {
        console.print(voucherService.allVouchersToString());
    }

}


