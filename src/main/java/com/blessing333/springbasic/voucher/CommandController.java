package com.blessing333.springbasic.voucher;

import com.blessing333.springbasic.ui.CommandOptionType;
import com.blessing333.springbasic.ui.UserInterface;
import com.blessing333.springbasic.ui.exception.CommandNotSupportedException;
import com.blessing333.springbasic.voucher.converter.Converter;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.voucher.service.VoucherService;
import com.blessing333.springbasic.voucher.validator.VoucherFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
            userInterface.printMessage(e.getMessage());
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
            userInterface.printVoucherCreateSuccess();
        } catch (VoucherCreateFailException e){
            userInterface.printMessage(e.getMessage());
        }
    }
}
