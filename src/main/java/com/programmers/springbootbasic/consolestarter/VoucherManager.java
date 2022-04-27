package com.programmers.springbootbasic.consolestarter;

import com.programmers.springbootbasic.io.ConsoleInput;
import com.programmers.springbootbasic.io.ConsoleOutputFormatPrinter;
import com.programmers.springbootbasic.io.StandardInput;
import com.programmers.springbootbasic.io.StandardOutput;
import com.programmers.springbootbasic.domain.Customer;
import com.programmers.springbootbasic.domain.Voucher;
import com.programmers.springbootbasic.service.CustomerVoucherLogService;
import com.programmers.springbootbasic.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.springbootbasic.io.ConsoleInput.*;
import static com.programmers.springbootbasic.io.ConsoleOutputFormatPrinter.DIVISION_LINE;
import static com.programmers.springbootbasic.validation.CustomerValidator.NON_EXISTING_CUSTOMER_HOLDING_THIS_VOUCHER_EXCEPTION_MESSAGE;
import static com.programmers.springbootbasic.validation.VoucherValidator.*;

@Component
public class VoucherManager {

    private final VoucherService voucherService;
    private final CustomerVoucherLogService customerVoucherLogService;

    private final StandardInput input = new ConsoleInput();
    private final StandardOutput output = new ConsoleOutputFormatPrinter();

    @Autowired
    public VoucherManager(VoucherService voucherService, CustomerVoucherLogService customerVoucherLogService) {
        this.voucherService = voucherService;
        this.customerVoucherLogService = customerVoucherLogService;
    }

    private void showVoucherMenu() {
        output.writeln(DIVISION_LINE);
        output.writeln("1. 새로운 할인권 생성");
        output.writeln("2. 아이디로 할인권 조회");
        output.writeln("3. 할당 가능한 할인권 조회");
        output.writeln("4. 모든 할인권 조회");
        output.writeln("5. 아이디로 해당 할인권 보유 고객 조회");
        output.writeln("6. 아이디로 할인권 삭제");
        output.writeln("7. 모든 할인권 삭제");
        output.writeln("8. 처음으로");
        output.writeln(DIVISION_LINE);
        output.write("==> ");
    }

    public void serviceVoucherWork() {
        showVoucherMenu();

        int response = Integer.parseInt(input.read());
        switch (response) {
            case 1 -> createNewVoucher();
            case 2 -> retrieveVoucherById();
            case 3 -> searchAvailableVouchers();
            case 4 -> retrieveAllVouchers();
            case 5 -> retrieveCustomerByVoucherId();
            case 6 -> deleteVoucher();
            case 7 -> deleteAllVouchers();
            case 8 -> output.writeln("처음으로 돌아갑니다.");
            default -> output.writeln(INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE);
        }
    }

    public void createNewVoucher() {
        String response = input.promptInput("할인권의 종류를 선택하세요.\n1. 고정금액 2. 고정비율");

        Voucher newVoucher = voucherService.createVoucher(Integer.parseInt(response));

        output.printNewVoucher(newVoucher);
    }

    public void retrieveVoucherById() {
        Voucher voucher = findAndGetVoucher();

        output.printFoundVoucher(voucher);
    }

    public void searchAvailableVouchers() {
        List<Voucher> availableVouchers = voucherService.findAvailableVouchers();

        output.printAvailableVouchers(availableVouchers);
    }

    public void retrieveAllVouchers() {
        List<Voucher> allVouchers = voucherService.getAllVouchers();
        output.printAllVouchers(allVouchers);
    }

    public void retrieveCustomerByVoucherId() {
        String voucherId = input.inputVoucherId();

        validateVoucherId(voucherId);

        Optional<Customer> foundCustomer = customerVoucherLogService.getCustomerHoldingVoucher(UUID.fromString(voucherId));

        output.printCustomerByVoucherId(foundCustomer.orElseThrow(() -> new IllegalArgumentException(NON_EXISTING_CUSTOMER_HOLDING_THIS_VOUCHER_EXCEPTION_MESSAGE)));
    }

    public void deleteVoucher() {
        Voucher voucherToBeDeleted = findAndGetVoucher();

        voucherService.deleteVoucherById(voucherToBeDeleted.getVoucherId());

        output.printDeletedVoucher(voucherToBeDeleted);
    }

    public void deleteAllVouchers() {
        String response = input.promptInput("정말로 모든 할인권을 삭제하시겠습니까(y/n)?").toLowerCase();

        switch (response) {
            case "y" -> {
                voucherService.deleteAllVouchers();
                output.writeln("모든 할인권이 정상적으로 삭제되었습니다.");
            }
            case "n" -> output.writeln("실행이 취소되었습니다.");
            default -> output.writeln(INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE);
        }
    }

    private Voucher findAndGetVoucher() {
        String voucherId = input.inputVoucherId();

        validateVoucherId(voucherId);

        Optional<Voucher> foundVoucher = voucherService.getVoucherById(UUID.fromString(voucherId));

        return foundVoucher.orElseThrow(() -> new IllegalArgumentException(NON_EXISTING_VOUCHER_ID_EXCEPTION_MESSAGE));
    }

    public static void validateVoucherId(String voucherId) {
        if (!isValidVoucherId(voucherId))
            throw new IllegalArgumentException(INVALID_VOUCHER_ID_EXCEPTION_MESSAGE);
    }

}
