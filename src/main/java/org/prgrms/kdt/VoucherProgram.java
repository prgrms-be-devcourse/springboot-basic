package org.prgrms.kdt;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.prgrms.kdt.engine.io.Input;
import org.prgrms.kdt.engine.io.Output;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherService;
import org.prgrms.kdt.voucher.VoucherType;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class VoucherProgram implements Runnable {
    private VoucherService voucherService;
    private Input input;
    private Output output;
    private String filePath;

    @SneakyThrows
    @Override
    public void run() {
        System.out.println(output.HELP);
        voucherService.loadVoucher(filePath);
        while (true) {
            String inputString = input.input(output.INPUT);
            Optional<Command> inputCommand = parse(inputString);

            if (inputCommand.isEmpty()) {
                output.printConsole(output.INPUT_ERROR);
                continue;
            }

            switch (inputCommand.get()) {
                case EXIT -> {
                    voucherService.saveVoucher(filePath);
                    return;
                }
                case CREATE -> {
                    // create voucher
                    Optional<Voucher> voucher = Optional.empty();
                    int voucherNum = Integer.parseInt(
                            input.input(output.INPUT_VOUCHER_NUM)
                    );

                    int discount = Integer.parseInt(
                            input.input(output.INPUT_DISCOUNT)
                    );

                    if (isInValidInput(voucherNum, discount)) output.printConsole(output.INPUT_ERROR);

                    if (voucherNum == VoucherType.FixedAmountVoucher.typeNum())
                        voucher = Optional.ofNullable(voucherService.createFixedAmountVoucher(discount));
                    else if (voucherNum == VoucherType.PercentDiscountVoucher.typeNum())
                        voucher = Optional.ofNullable(voucherService.createPercentDiscountVoucher(discount));

                    // print voucher created
                    if (voucher.isEmpty()) output.printConsole(output.CREATE_VOUCHER_ERROR);
                    if (voucher.isPresent()) {
                        output.printConsole(MessageFormat.format(
                                "{0} 타입의 voucher를 생성하였습니다.",
                                voucher.get().getType())
                        );
                    }
                }
                case LIST -> {
                    Map<UUID, Voucher> voucherList = voucherService.getVoucherList();
                    if (voucherList.isEmpty()) {
                        output.printConsole(output.NO_VOUCHER);
                    } else {
                        for (var voucher : voucherList.values()) {
                            output.printConsole(voucher.toString());
                        }
                    }
                }
            }
        }

    }

    private boolean isInValidInput(int voucherNum, int discount) {
        if (discount < 0) return false;
        if (voucherNum != VoucherType.FixedAmountVoucher.typeNum()
                && voucherNum != VoucherType.PercentDiscountVoucher.typeNum()) return false;
        if (voucherNum == VoucherType.PercentDiscountVoucher.typeNum() && discount > 100) return false;
        return true;
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
