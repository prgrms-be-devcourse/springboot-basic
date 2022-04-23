package org.prgrms.kdt;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.model.customer.CustomerGrade;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.service.customer.CustomerService;
import org.prgrms.kdt.service.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.stream.Collectors;

public class CommandLineRunner implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CommandLineRunner.class);

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;

    private CommandType commandType;

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
        commandType = CommandType.INVALID;
        while (commandType.isRunnable()) {
            String commandManuals = commandType.getCommandManuals();
            output.printCommandManual(commandManuals);

            command = input.input();
            commandType = CommandType.getCommandType(command);
            switch (commandType) {
                case EXIT -> exitCommandRunner();
                case CREATE -> createVoucher();
                case UPDATE -> updateVoucher();
                case DELETE -> deleteVoucher();
                case LIST -> findVoucherList();
                case BLACK_LIST -> findBlackList();
                default -> handleInValidCommand();
            }
        }
    }

    private void exitCommandRunner() {
        commandType = CommandType.EXIT;
        output.printShutDownSystem();
    }

    private void handleInValidCommand() {
        output.printInvalidCommand();
    }

    private void createVoucher() {
        output.printMessage("바우처 타입을 선택해주세요.\n" + VoucherType.getAllVoucherManual());
        VoucherType voucherType = VoucherType.getVoucherType(input.input())
            .orElse(null);
        if (voucherType == null) {
            output.printMessage("유효하지 않은 바우처 타입입니다.");
            return;
        }

        output.printVoucherValue(voucherType);
        long voucherValue = input.inputLong();

        try {
            Voucher voucher = voucherService.create(UUID.randomUUID(), voucherValue, voucherType);
            output.printMessage("바우처 생성에 성공하였습니다. " + voucher.toString());
        } catch (IllegalArgumentException e) {
            logger.warn("[Voucher] create error: {}", e.getMessage(), e);
            output.printMessage(e.getMessage());
        }
    }

    private void updateVoucher() {
        output.printMessage("수정할 바우처 ID를 입력해주세요.");
        UUID voucherId = input.inputUUID()
            .orElse(null);
        if (voucherId == null) {
            output.printMessage("유효하지 않은 바우처 ID입니다.");
            return;
        }

        output.printMessage("수정할 바우처 값을 입력해주세요.");
        long voucherValue = input.inputLong();

        try {
            Voucher voucher = voucherService.update(voucherId, voucherValue);
            output.printMessage("바우처 수정에 성공하였습니다. " + voucher.toString());
        } catch (RuntimeException e) {
            logger.warn("[Voucher] update error: {}", e.getMessage(), e);
            output.printMessage(e.getMessage());
        }
    }

    private void deleteVoucher() {
        output.printMessage("삭제할 바우처 ID를 입력해주세요.");
        UUID voucherId = input.inputUUID()
            .orElse(null);
        if (voucherId == null) {
            output.printMessage("유효하지 않은 바우처 ID입니다.");
            return;
        }

        try {
            voucherService.delete(voucherId);
            output.printMessage("바우처 삭제에 성공하였습니다. voucherId=" + voucherId);
        } catch (RuntimeException e) {
            logger.warn("[Voucher] delete error: {}", e.getMessage(), e);
            output.printMessage(e.getMessage());
        }
    }

    private void findVoucherList() {
        String vouchers = voucherService.findAll().stream()
            .map(Object::toString)
            .collect(Collectors.joining(",\n"));
        output.printMessage(vouchers);
    }

    private void findBlackList() {
        String blackListCustomers = customerService.findAllBlackList(CustomerGrade.BLACK_LIST).stream()
            .map(Object::toString)
            .collect(Collectors.joining(",\n"));
        output.printMessage(blackListCustomers);
    }
}
