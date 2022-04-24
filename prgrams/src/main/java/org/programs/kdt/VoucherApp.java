package org.programs.kdt;

import lombok.RequiredArgsConstructor;
import org.programs.kdt.Command.VoucherMenu;
import org.programs.kdt.Exception.BusinessException;
import org.programs.kdt.IO.Console;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Voucher.domain.VoucherType;
import org.programs.kdt.Voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.programs.kdt.Exception.ErrorCode.INVALID_COMMAND_TYPE;
import static org.programs.kdt.Exception.ErrorCode.INVALID_ENUM_TYPE;

@RequiredArgsConstructor
@Component
public class VoucherApp {

    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private final Console console;
    private final VoucherService voucherService;

    public void voucherProcess() {

        VoucherMenu voucherMenu =  VoucherMenu.ERROR;
        while(!voucherMenu.equals(VoucherMenu.RETURN)) {
            console.output(VoucherMenu.toMenu());
            String input = console.input("명령어를 입력해주세요");
            voucherMenu = VoucherMenu.find(input);
            voucherMenuProcess(voucherMenu);
        }
    }

    private void voucherMenuProcess(VoucherMenu voucherMenu) {
        switch (voucherMenu) {
            case CREATE:
                voucherCreateProcess();
                break;
            case FIND_ALL:
                voucherFindAllProcess();
                break;
            case ERROR:
                logger.error(INVALID_COMMAND_TYPE.getMessage());
                break;
            case DELETE:
                voucherDeleteProcess();
                break;
            case RETURN:
                break;
            case FIND_FIX:
                voucherFindFixedAmount();
                break;
            case FIND_PERCENT:
                voucherFindPercent();
                break;
            default:
                logger.error(INVALID_ENUM_TYPE.getMessage());
        }
    }

    private void voucherFindPercent() {
        List<Voucher> voucherList = voucherService.findByType(VoucherType.PERCENT);
        console.printVoucherList(voucherList);
    }

    private void voucherFindFixedAmount() {
        List<Voucher> voucherList = voucherService.findByType(VoucherType.FIXEDAMOUNT);
        console.printVoucherList(voucherList);
    }

    private void voucherDeleteProcess() {
        UUID uuid = console.inputUUID("바우처 id를 입력해주세요.");
        try {
        voucherService.delete(uuid);
        } catch (BusinessException e) {
            console.output(e.getMessage());
        }
    }

    private void voucherFindAllProcess() {
        List<Voucher> voucherList = voucherService.findAll();
        console.printVoucherList(voucherList);
    }

    private void voucherCreateProcess() {
        String inputVoucherType = console.input("바우처 타입을 입력해주세요(fiexedAmount, percent)");

        try {
            VoucherType voucherType = VoucherType.findVoucherType(inputVoucherType);
            String inputVoucherValue = console.input("바우처 할인금액(율)을 입력해주세요");
            Voucher voucher = voucherType.createVoucher(UUID.randomUUID(),Long.parseLong(inputVoucherValue), LocalDateTime.now());
            voucherService.insert(voucher);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
