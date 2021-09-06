package org.prgrms.kdt;

import lombok.SneakyThrows;
import org.prgrms.kdt.customer.service.CustomerServiceImpl;
import org.prgrms.kdt.engine.Command;
import org.prgrms.kdt.engine.Console;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherService;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class VoucherProgram implements Runnable {
    private final VoucherService voucherService;
    private final CustomerServiceImpl customerService;
    private final Console console;

    public VoucherProgram(VoucherService voucherService, CustomerServiceImpl customerService, Console console) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.console = console;
    }

    @SneakyThrows
    @Override
    public void run() {
        console.printConsole(console.HELP);
        while (true) {
            String inputString = console.input(console.INPUT);
            Optional<Command> inputCommand = parse(inputString);

            if (inputCommand.isEmpty()) {
                console.logError(console.INPUT_ERROR);
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
                            console.input(console.INPUT_VOUCHER_NUM)
                    );

                    int discount = Integer.parseInt(
                            console.input(console.INPUT_DISCOUNT)
                    );

                    if (isInValidInput(voucherNum, discount)) {
                        console.logError(console.INPUT_ERROR);
                        continue;
                    }

                    if (voucherNum == VoucherType.FixedAmountVoucher.typeNum())
                        voucher = Optional.ofNullable(voucherService.createFixedAmountVoucher(discount));
                    else if (voucherNum == VoucherType.PercentDiscountVoucher.typeNum())
                        voucher = Optional.ofNullable(voucherService.createPercentDiscountVoucher(discount));

                    // print voucher created
                    if (voucher.isEmpty()) console.logInfo(console.CREATE_VOUCHER_ERROR);
                    voucher.ifPresent(value -> console.printConsole(MessageFormat.format(
                            "{0} 타입의 voucher를 생성하였습니다.",
                            value.getType())
                    ));
                }
                case LIST -> {
                    Map<UUID, Voucher> voucherList = voucherService.getVoucherList();
                    if (voucherList.isEmpty()) {
                        console.logError(console.NO_VOUCHER);
                    } else {
                        for (var voucher : voucherList.values()) {
                            console.printConsole(voucher.toString());
                        }
                    }
                }
                case BLACKLIST -> {
                    ClassPathResource resource = new ClassPathResource("blacklist/customer_blacklist.csv");
                    Optional<Map<Integer, String>> blackList = customerService.loadBlackList(resource);
                    if (blackList.isEmpty()) {
                        console.logError(console.NO_BLACKLIST);
                    } else {
                        for (var customer : blackList.get().values()) {
                            console.printConsole(customer);
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
