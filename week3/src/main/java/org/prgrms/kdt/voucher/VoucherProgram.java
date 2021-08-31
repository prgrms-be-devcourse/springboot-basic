package org.prgrms.kdt.voucher;

import org.prgrms.kdt.io.UserInteraction;
import org.prgrms.kdt.io.CommandType;
import org.prgrms.kdt.voucher.model.FixedAmountVoucher;
import org.prgrms.kdt.voucher.model.PercentDiscountVoucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.blacklist.BlackListrService;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

@Component
public class VoucherProgram {
    private final VoucherService voucherService;
    private final UserInteraction userInteraction;
    private final BlackListrService blackListService;

    public VoucherProgram(
            BlackListrService blackListrService
            , VoucherService voucherService
            , UserInteraction userInteraction) {

        this.voucherService = voucherService;
        this.userInteraction = userInteraction;
        this.blackListService = blackListrService;
    }

    public void runProgram() throws IOException {

        Scanner sc = new Scanner(System.in);
        String input = null;

        while (true) {
            userInteraction.showInfoMessage();
            input = sc.next();

            switch (CommandType.getCommandType(input)) {
                case CREATE -> {
                    userInteraction.showVoucherTypeSelectMessage();
                    UUID uuid = UUID.randomUUID();
                    String discountType = sc.next();
                    userInteraction.showVoucherDiscountMessage();
                    long amount = Long.parseLong(sc.next());

                    switch (VoucherType.getVoucherType(discountType)) {
                        case FIXED_AMOUNT_VOUCHER -> voucherService.create(new FixedAmountVoucher(uuid, amount));
                        case PERCENT_DISCOUNT_VOUCHER -> voucherService.create(new PercentDiscountVoucher(uuid, amount));
                        default -> userInteraction.showInvalidTypeMessage();
                    }
                }
                case LIST -> {
                    userInteraction.showVoucherList(voucherService.list());
                }
                case BLACK_LIST -> {
                    userInteraction.showBlackList(blackListService.list());
                }
                case EXIT -> {
                    userInteraction.showExitProgramMessage();
                    System.exit(0);
                }
                case UNKNOWN -> {
                    userInteraction.showInvalidMessage();
                }
            }
        }
    }
}
