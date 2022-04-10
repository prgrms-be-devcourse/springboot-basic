package com.blessing333.springbasic.voucher;

import com.blessing333.springbasic.ui.CommandOptionType;
import com.blessing333.springbasic.ui.UserInterface;
import com.blessing333.springbasic.ui.exception.CommandNotSupportedException;
import com.blessing333.springbasic.voucher.converter.Converter;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.voucher.exception.VoucherCreateFailException;
import com.blessing333.springbasic.voucher.service.VoucherService;
import com.blessing333.springbasic.voucher.validator.VoucherFormValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandController {
    private final VoucherFormValidator validator;
    private final VoucherService voucherService;
    private final UserInterface userInterface;
    private final Converter converter;

    public boolean parseCommand(String command){
        boolean isQuit = false;
        try {
            CommandOptionType type = CommandOptionType.find(command);
            switch (type) {
                case CREATE -> startCreateNewVoucher();
                case LIST -> voucherService.showVoucherList();
                case QUIT -> isQuit = true;
                default -> userInterface.showHelpText();
            }
        } catch (CommandNotSupportedException e) {
            log.error(e.getMessage());
            userInterface.showHelpText();
        }
        return isQuit;
    }

    private void startCreateNewVoucher(){
        try {
            VoucherCreateForm form = userInterface.requestVoucherInformation();
            validator.validVoucherCreateForm(form);
            ConvertedVoucherCreateForm convertedForm = converter.convert(form);
            voucherService.createNewVoucher(convertedForm);
            userInterface.printVoucherCreateSuccessMessage();
        } catch (VoucherCreateFailException e){
            log.error(e.getMessage());
        }
    }
}
