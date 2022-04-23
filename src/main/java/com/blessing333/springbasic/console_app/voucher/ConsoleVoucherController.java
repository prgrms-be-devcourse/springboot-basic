package com.blessing333.springbasic.console_app.voucher;

import com.blessing333.springbasic.common.util.ExceptionStackTraceConverter;
import com.blessing333.springbasic.console_app.RunnableController;
import com.blessing333.springbasic.console_app.ui.CommandNotSupportedException;
import com.blessing333.springbasic.console_app.voucher.ui.VoucherCommandOptionType;
import com.blessing333.springbasic.console_app.voucher.ui.VoucherManagerUserInterface;
import com.blessing333.springbasic.voucher.converter.Converter;
import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.voucher.exception.VoucherCreateFailException;
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
    private final Converter converter;

    @Override
    public void startService() {
        boolean isExit = false;
        while(!isExit){
            userInterface.showGuideText();
            String command = userInterface.inputMessage();
            try {
                VoucherCommandOptionType type = VoucherCommandOptionType.find(command);
                switch (type) {
                    case CREATE -> startCreateNewVoucher();
                    case LIST -> showAllVoucherInformation();
                    case QUIT -> isExit = true;
                    default -> userInterface.showHelpText();
                }
            } catch (CommandNotSupportedException e) {
                log.error(ExceptionStackTraceConverter.convertToString(e));
                userInterface.showHelpText();
            }
        }
    }

    private void startCreateNewVoucher(){
        try {
            VoucherCreateForm form = userInterface.requestVoucherInformation();
            ConvertedVoucherCreateForm convertedForm = converter.convert(form);
            Voucher newVoucher = voucherService.createNewVoucher(convertedForm);
            userInterface.printVoucherCreateSuccessMessage(newVoucher);
        } catch (VoucherCreateFailException e){
            log.error(ExceptionStackTraceConverter.convertToString(e));
            userInterface.printMessage(e.getMessage());
        }
    }

    private void showAllVoucherInformation(){
        List<Voucher> vouchers = voucherService.loadAllVoucher();
        vouchers.forEach(userInterface::printVoucherInformation);
    }
}
