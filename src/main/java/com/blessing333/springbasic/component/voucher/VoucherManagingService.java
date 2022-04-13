package com.blessing333.springbasic.component.voucher;

import com.blessing333.springbasic.RunnableService;
import com.blessing333.springbasic.component.voucher.command.CommandOptionType;
import com.blessing333.springbasic.component.voucher.converter.Converter;
import com.blessing333.springbasic.component.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.component.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.component.voucher.exception.VoucherCreateFailException;
import com.blessing333.springbasic.component.voucher.service.VoucherService;
import com.blessing333.springbasic.component.voucher.ui.VoucherManagerUserInterface;
import com.blessing333.springbasic.component.voucher.ui.exception.CommandNotSupportedException;
import com.blessing333.springbasic.component.voucher.validator.VoucherFormValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VoucherManagingService implements RunnableService {
    private final VoucherFormValidator validator;
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
                CommandOptionType type = CommandOptionType.find(command);
                switch (type) {
                    case CREATE -> startCreateNewVoucher();
                    case LIST -> voucherService.showVoucherList();
                    case QUIT -> isExit = true;
                    default -> userInterface.showHelpText();
                }
            } catch (CommandNotSupportedException e) {
                log.error(e.getMessage());
                userInterface.showHelpText();
            }
        }

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
