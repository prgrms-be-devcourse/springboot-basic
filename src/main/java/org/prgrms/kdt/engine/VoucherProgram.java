package org.prgrms.kdt.engine;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.prgrms.kdt.Command;
import org.prgrms.kdt.engine.io.Input;
import org.prgrms.kdt.engine.io.Output;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class VoucherProgram implements Runnable {
    private VoucherService voucherService;
    private Input input;
    private Output output;

    @SneakyThrows
    @Override
    public void run() {
        output.help();
        while (true) {
            String inputString = input.input("명령어를 입력하세요.");
            Optional<Command> inputCommand = parse(inputString);
            if (inputCommand.isEmpty()) {
                output.inputError();
                continue;
            }

            if (inputCommand.equals(Optional.of(Command.EXIT))) {
                break;
            }
            else if (inputCommand.equals(Optional.of(Command.CREATE))) {
                // create voucher
                int voucherType = Integer.parseInt(
                        input.input("""
                                원하는 종류의 voucher 번호를 입력하세요.
                                1. FixedAmountVoucher
                                2. PercentDiscountVoucher""")
                );

                int discount = Integer.parseInt(
                        input.input("원하는 할인금액 또는 할인율을 입력해주세요.")
                );

                if (discount > 0) {
                    if (voucherType == 1) {
                        // FixedAmountVoucher
                        voucherService.createFixedAmountVoucher(discount);
                    } else if (voucherType == 2) {
                        // PercentDiscountVoucher
                        voucherService.createPercentDiscountVoucher(discount);
                    } else {
                        output.inputError();
                    }
                } else {
                    output.inputError();
                }
            }
            else if ((inputCommand.equals(Optional.of(Command.LIST)))) {
                // list voucher
                List<Voucher> voucherList = voucherService.getVoucherList();
                for (Voucher voucher: voucherList) {
                    System.out.println(voucher);
                }
            }
        }

    }

    private Optional<Command> parse(String inputString) {
        return switch (inputString) {
            case "exit" -> Optional.of(Command.EXIT);
            case "create" -> Optional.of(Command.CREATE);
            case "list" -> Optional.of(Command.LIST);
            default -> Optional.empty();
        };
    }
}
