package org.prgrms.kdt;

import lombok.SneakyThrows;
import org.prgrms.kdt.customer.CustomerService;
import org.prgrms.kdt.engine.io.Input;
import org.prgrms.kdt.engine.io.Output;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherService;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.core.io.Resource;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class VoucherProgram implements Runnable {
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final Resource resource;
    private final Input input;
    private final Output output;

    public VoucherProgram(VoucherService voucherService, CustomerService customerService, Resource resource, Console console) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.resource = resource;
        this.input = console;
        this.output = console;
    }

    @SneakyThrows
    @Override
    public void run() {
        output.printConsole(output.HELP);
        while (true) {
            String inputString = input.input(output.INPUT);
            Optional<Command> inputCommand = parse(inputString);

            if (inputCommand.isEmpty()) {
                output.logError(output.INPUT_ERROR);
                continue;
            }

            switch (inputCommand.get()) {
                case EXIT -> {
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

                    if (isInValidInput(voucherNum, discount)) {
                        output.logError(output.INPUT_ERROR);
                        continue;
                    }

                    if (voucherNum == VoucherType.FixedAmountVoucher.typeNum())
                        voucher = Optional.ofNullable(voucherService.createFixedAmountVoucher(discount));
                    else if (voucherNum == VoucherType.PercentDiscountVoucher.typeNum())
                        voucher = Optional.ofNullable(voucherService.createPercentDiscountVoucher(discount));

                    // print voucher created
                    if (voucher.isEmpty()) output.logInfo(output.CREATE_VOUCHER_ERROR);
                    voucher.ifPresent(value -> output.printConsole(MessageFormat.format(
                            "{0} 타입의 voucher를 생성하였습니다.",
                            value.getType())
                    ));
                }
                case LIST -> {
                    Map<UUID, Voucher> voucherList = voucherService.getVoucherList();
                    if (voucherList.isEmpty()) {
                        output.logError(output.NO_VOUCHER);
                    } else {
                        for (var voucher : voucherList.values()) {
                            output.printConsole(voucher.toString());
                        }
                    }
                }
                case BLACKLIST -> {
                    Optional<Map<Integer, String>> blackList = customerService.loadBlackList(resource);
                    if (blackList.isEmpty()) {
                        output.logError(output.NO_BLACKLIST);
                    } else {
                        for (var customer : blackList.get().values()) {
                            output.printConsole(customer);
                        }
                    }
                }
            }
        }

    }

    private boolean isInValidInput(int voucherNum, int discount) {
        if (discount < 0) return true;
        if (voucherNum != VoucherType.FixedAmountVoucher.typeNum()
                && voucherNum != VoucherType.PercentDiscountVoucher.typeNum()) return true;
        if (voucherNum == VoucherType.PercentDiscountVoucher.typeNum() && discount > 100) return true;
        return false;
    }

    private Optional<Command> parse(String inputString) {
        return switch (inputString) {
            case "exit" -> Optional.of(Command.EXIT);
            case "create" -> Optional.of(Command.CREATE);
            case "list" -> Optional.of(Command.LIST);
            case "blacklist" -> Optional.of(Command.BLACKLIST);
            default -> Optional.empty();
        };
    }
}
