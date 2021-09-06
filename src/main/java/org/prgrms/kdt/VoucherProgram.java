package org.prgrms.kdt;

import lombok.SneakyThrows;
import org.prgrms.kdt.customer.service.CustomerServiceImpl;
import org.prgrms.kdt.engine.Command;
import org.prgrms.kdt.engine.Console;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.prgrms.kdt.voucher.VoucherType;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class VoucherProgram implements Runnable {
    private final VoucherService voucherService;
    private final CustomerServiceImpl customerService;
    private final Console console;
    private final MessageSource messageSource;

    public VoucherProgram(VoucherService voucherService, CustomerServiceImpl customerService, Console console, MessageSource messageSource) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.console = console;
        this.messageSource = messageSource;
    }

    @SneakyThrows
    @Override
    public void run() {
        console.printConsole(messageSource.getMessage("help", null, Locale.getDefault()));
        while (true) {
            String inputString = console.input(messageSource.getMessage("input", null, Locale.getDefault()));
            Optional<Command> inputCommand = parse(inputString);

            if (inputCommand.isEmpty()) {
                console.logError(messageSource.getMessage("error.input", null, Locale.getDefault()));
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
                            console.input(messageSource.getMessage("inputVoucherNum", null, Locale.getDefault()))
                    );

                    int discount = Integer.parseInt(
                            console.input(messageSource.getMessage("inputDiscount", null, Locale.getDefault()))
                    );

                    if (isInValidInput(voucherNum, discount)) {
                        console.logError(messageSource.getMessage("error.input", null, Locale.getDefault()));
                        continue;
                    }

                    if (voucherNum == VoucherType.FixedAmountVoucher.ordinal())
                        voucher = Optional.ofNullable(voucherService.createFixedAmountVoucher(discount));
                    else if (voucherNum == VoucherType.PercentDiscountVoucher.ordinal())
                        voucher = Optional.ofNullable(voucherService.createPercentDiscountVoucher(discount));

                    // print voucher created
                    if (voucher.isEmpty())
                        console.logInfo(messageSource.getMessage("error.createVoucher", null, Locale.getDefault()));
                    voucher.ifPresent(value -> console.printConsole(
                            messageSource.getMessage("createVoucher", new String[] {value.getType().name()}, Locale.getDefault()))
                    );
                }
                case LIST -> {
                    Map<UUID, Voucher> voucherList = voucherService.getVoucherList();
                    if (voucherList.isEmpty()) {
                        console.logError(messageSource.getMessage("error.noVoucher", null, Locale.getDefault()));
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
                        console.logError(messageSource.getMessage("error.noBlacklist", null, Locale.getDefault()));
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
        if (voucherNum != VoucherType.FixedAmountVoucher.ordinal()
                && voucherNum != VoucherType.PercentDiscountVoucher.ordinal()) return true;
        if (voucherNum == VoucherType.PercentDiscountVoucher.ordinal() && discount > 100) return true;
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
