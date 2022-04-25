package org.prgrms.kdt;

import org.prgrms.kdt.error.InputException;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.model.customer.CustomerType;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.service.customer.CustomerService;
import org.prgrms.kdt.service.voucher.VoucherService;

import java.util.UUID;
import java.util.stream.Collectors;

public class CommandLineRunner implements Runnable {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandLineRunner(
        Input input,
        Output output,
        VoucherService voucherService,
        CustomerService customerService
    ) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run() {

        String command;
        CommandType commandType = CommandType.INVALID;
        while (commandType.isRunnable()) {
            String commandManuals = commandType.getCommandManuals();
            output.printCommandManual(commandManuals);

            try {
                command = input.input();
                commandType = CommandType.getCommandType(command);
                switch (commandType) {
                    case EXIT -> {
                        commandType = exitCommandRunner();
                    }
                    case CREATE -> createVoucher();
                    case UPDATE -> updateVoucher();
                    case DELETE -> deleteVoucher();
                    case LIST -> findVoucherList();
                    case BLACK_LIST -> findBlackList();
                    default -> handleInValidCommand();
                }
            } catch (IllegalArgumentException | InputException e) {
                output.printWarnMessage(e);
            } catch (RuntimeException e) {
                output.printErrorMessage(e);
            }
        }
    }

    private CommandType exitCommandRunner() {
        output.printMessage("시스템이 종료되었습니다.");
        return CommandType.EXIT;
    }

    private void handleInValidCommand() {
        output.printMessage("유효하지 않은 명령입니다.");
    }

    private void createVoucher() {
        output.printMessage("바우처 타입을 선택해주세요.\n" + VoucherType.getAllVoucherManual());
        VoucherType voucherType = VoucherType.getVoucherType(input.input());

        output.printVoucherValue(voucherType);
        long voucherValue = input.inputLong();

        Voucher voucher = voucherService.create(UUID.randomUUID(), voucherValue, voucherType);
        output.printMessage("바우처 생성에 성공하였습니다. " + voucher.toString());
    }

    private void updateVoucher() {
        output.printMessage("수정할 바우처 ID를 입력해주세요.");
        UUID voucherId = input.inputUUID();

        output.printMessage("수정할 바우처 값을 입력해주세요.");
        long voucherValue = input.inputLong();

        Voucher voucher = voucherService.update(voucherId, voucherValue);
        output.printMessage("바우처 수정에 성공하였습니다. " + voucher.toString());
    }

    private void deleteVoucher() {
        output.printMessage("삭제할 바우처 ID를 입력해주세요.");
        UUID voucherId = input.inputUUID();

        voucherService.delete(voucherId);
        output.printMessage("바우처 삭제에 성공하였습니다. voucherId=" + voucherId);
    }

    private void findVoucherList() {
        String vouchers = voucherService.findAll().stream()
            .map(Object::toString)
            .collect(Collectors.joining(",\n"));

        output.printMessage(vouchers);
    }

    private void findBlackList() {
        String blackListCustomers = customerService.findAllByCustomerType(CustomerType.BLACK_LIST).stream()
            .map(Object::toString)
            .collect(Collectors.joining(",\n"));

        output.printMessage(blackListCustomers);
    }
}
