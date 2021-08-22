package org.prgrms.kdt;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.prgrms.kdt.Command;
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
        output.help();
        voucherService.loadVoucher(filePath);
        while (true) {
            String inputString = input.input("명령어를 입력하세요.");
            Optional<Command> inputCommand = parse(inputString);

            if (inputCommand.isEmpty()) {
                output.inputError();
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
                            input.input("""
                                    원하는 종류의 voucher 번호를 입력하세요.
                                    1. FixedAmountVoucher
                                    2. PercentDiscountVoucher""")
                    );

                    int discount = Integer.parseInt(
                            input.input("원하는 할인금액 또는 할인율을 입력해주세요.")
                    );

                    if (isInValidInput(voucherNum, discount)) output.inputError();

                    if (voucherNum == VoucherType.FixedAmountVoucher.typeNum())
                        voucher = Optional.ofNullable(voucherService.createFixedAmountVoucher(discount));
                    else if (voucherNum == VoucherType.PercentDiscountVoucher.typeNum())
                        voucher = Optional.ofNullable(voucherService.createPercentDiscountVoucher(discount));

                    // print voucher created
                    if (voucher.isEmpty()) System.out.println("voucher를 정상적으로 생성하지 못했습니다.");
                    if (voucher.isPresent()) {
                        System.out.println(MessageFormat.format(
                                "{0} 타입의 voucher를 생성하였습니다.",
                                voucher.get().getType())
                        );
                    }
                }
                case LIST -> {
                    Map<UUID, Voucher> voucherList = voucherService.getVoucherList();
                    if (voucherList.isEmpty()) {
                        System.out.println("voucher가 없습니다.");
                    } else {
                        for (var voucher : voucherList.values()) {
                            System.out.println(voucher);
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
