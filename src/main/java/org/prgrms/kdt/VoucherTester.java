package org.prgrms.kdt;

import org.prgrms.kdt.Exception.InputFormatException;
import org.prgrms.kdt.Exception.VoucherTypeNotMatchException;
import org.prgrms.kdt.util.Console;
import org.prgrms.kdt.util.CommandType;
import org.prgrms.kdt.voucher.*;
import org.prgrms.kdt.IO.Input;
import org.prgrms.kdt.IO.Output;
import org.prgrms.kdt.util.VoucherTesterGuideComment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class VoucherTester {

    private final VoucherService voucherService;
    private final VoucherRepository voucherRepository;

    public VoucherTester(VoucherService voucherService, VoucherRepository voucherRepository){
        this.voucherService = voucherService;
        this.voucherRepository = voucherRepository;
    }

    public void run() {
        boolean exitProgram = false;
        Input input = new Console();
        Output output = new Console();

        while (!exitProgram) {
            try {
                CommandType commandType = CommandType.matchCommandType(input.input(VoucherTesterGuideComment.commandGuide));

                switch (commandType) {
                    case EXIT:
                        output.print(VoucherTesterGuideComment.exitMsg);
                        exitProgram = true;
                        break;

                    case CREATE:
                        VoucherType voucherType;
                        try {
                            voucherType = VoucherType.matchVoucherType(input.input(VoucherTesterGuideComment.voucherGuide));
                        } catch (VoucherTypeNotMatchException voucherTypeNotMatchException){
                            output.print(VoucherTesterGuideComment.voucherNotMatchErrorMsg);
                            break;
                        }

                        int discountInfo = Integer.parseInt(input.input(VoucherTesterGuideComment.voucherInfoGuide));
                        if (!isValidVoucherInfo(voucherType, discountInfo)) {
                            output.print(VoucherTesterGuideComment.inputErrorMsg);
                            break;
                        }

                        var voucher = voucherService.createVoucher(UUID.randomUUID(), voucherType, discountInfo, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
                        if (voucher != null) output.print(VoucherTesterGuideComment.voucherCreateSuccess);
                        else output.print(VoucherTesterGuideComment.voucherCreateFail);
                        break;

                    case LIST:
                        var listType = input.input(VoucherTesterGuideComment.voucherMatcherGuide);
                        try {
                            output.print(voucherRepository.getListByType(VoucherType.matchVoucherType(listType)));
                        } catch (VoucherTypeNotMatchException voucherTypeNotMatchException) {
                            output.print(VoucherTesterGuideComment.voucherNotMatchErrorMsg);
                        }
                        break;

                    default:
                        output.print(VoucherTesterGuideComment.commandInputErrorMsg);
                        break;
                }
                output.print(VoucherTesterGuideComment.endLine);
            } catch (InputFormatException inputCommandException){
                output.print(VoucherTesterGuideComment.commandInputErrorMsg);
            }
        }
    }

    private boolean isValidVoucherInfo(VoucherType voucherType, int discountInfo) {
        switch (voucherType) {
            case FIXED:
                if (discountInfo < 0) return false;
                break;
            case PERCENT:
                if (discountInfo < 1 || discountInfo > 100) return false;
                break;
        }
        return true;
    }

}

