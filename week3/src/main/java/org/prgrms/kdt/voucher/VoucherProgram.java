package org.prgrms.kdt.voucher;

import org.prgrms.kdt.blacklist.BlackListrService;
import org.prgrms.kdt.io.CommandType;
import org.prgrms.kdt.io.UserInteraction;
import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;

@Component
public class VoucherProgram {
    private final VoucherService voucherService;
    private final UserInteraction userInteraction;
    private final BlackListrService blackListService;

    private final Scanner sc = new Scanner(System.in);

    public VoucherProgram(
            BlackListrService blackListrService
            , VoucherService voucherService
            , UserInteraction userInteraction) {

        this.voucherService = voucherService;
        this.userInteraction = userInteraction;
        this.blackListService = blackListrService;
    }

    public void runProgram() throws IOException {

        String input;

        while (true) {
            userInteraction.showInfoMessage();
            input = userInteraction.getNext(sc);

            switch (CommandType.getCommandType(input)) {
                case CREATE -> {
                    createVoucher();
                }
                case LIST -> {
                    userInteraction.showVoucherList(voucherService.list());
                }
                case BLACK_LIST -> {
                    userInteraction.showBlackList(blackListService.list());
                }
                case EXIT -> {
                    userInteraction.showExitProgramMessage();
                    return;
                }
                default -> {
                    userInteraction.showInvalidMessage();
                }
            }
        }
    }

    private void createVoucher() throws IOException {
        userInteraction.showVoucherTypeSelectMessage();
        UUID uuid = UUID.randomUUID();
        String discountType = userInteraction.getNext(sc);

        userInteraction.showVoucherDiscountMessage();
        long amount = Long.parseLong(userInteraction.getNext(sc));

        checkDiscountType(uuid, VoucherType.getVoucherType(discountType), amount);
    }

    private void checkDiscountType(UUID uuid, VoucherType voucherType, long amount) throws IOException {

        switch (voucherType) {
            case FIXED_AMOUNT_VOUCHER -> voucherService.create(new FixedAmountVoucher(uuid, amount, voucherType, LocalDateTime.now()));
            case PERCENT_DISCOUNT_VOUCHER -> voucherService.create(new PercentDiscountVoucher(uuid, amount, voucherType, LocalDateTime.now()));
            default -> userInteraction.showInvalidTypeMessage();
        }
    }
}
