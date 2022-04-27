package com.blessing333.springbasic.console_app.voucher;

import com.blessing333.springbasic.console_app.RunnableController;
import com.blessing333.springbasic.console_app.ui.CommandNotSupportedException;
import com.blessing333.springbasic.console_app.voucher.ui.VoucherCommandOptionType;
import com.blessing333.springbasic.console_app.voucher.ui.VoucherManagerUserInterface;
import com.blessing333.springbasic.voucher.converter.VoucherPayloadConverter;
import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.domain.VoucherCreateFailException;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateFormPayload;
import com.blessing333.springbasic.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("console")
@RequiredArgsConstructor
@Slf4j
public class ConsoleVoucherController implements RunnableController {
    private final VoucherService voucherService;
    private final VoucherManagerUserInterface userInterface;
    private final VoucherPayloadConverter voucherPayloadConverter;

    @Override
    public void startService() {
        boolean isExit = false;
        while(!isExit){
            userInterface.printGuide();
            String command = userInterface.requestMessage();
            try {
                VoucherCommandOptionType type = VoucherCommandOptionType.find(command);
                switch (type) {
                    case CREATE -> startCreateNewVoucher();
                    case LIST -> showAllVoucherInformation();
                    case QUIT -> isExit = true;
                    default -> userInterface.printHelp();
                }
            } catch (CommandNotSupportedException e) {
                log.error(e.getMessage(),e);
                userInterface.printHelp();
            }
        }
    }

    private void startCreateNewVoucher(){
        try {
            VoucherCreateFormPayload form = userInterface.requestVoucherInformation();
            VoucherCreateForm convertedForm = voucherPayloadConverter.toCreateForm(form);
            Voucher newVoucher = voucherService.registerVoucher(convertedForm);
            userInterface.printRegisterComplete(newVoucher);
        } catch (VoucherCreateFailException e){
            log.error(e.getMessage(),e);
            userInterface.printMessage(e.getMessage());
        }
    }

    private void showAllVoucherInformation(){
        List<Voucher> vouchers = voucherService.loadAllVoucher();
        vouchers.forEach(userInterface::printVoucherInformation);
    }
}
