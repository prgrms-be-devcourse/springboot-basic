package com.blessing333.springbasic.voucher;

import com.blessing333.springbasic.RunnableService;
import com.blessing333.springbasic.voucher.converter.Converter;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.voucher.exception.VoucherCreateFailException;
import com.blessing333.springbasic.voucher.service.VoucherService;
import com.blessing333.springbasic.voucher.ui.VoucherManagerServiceUserInterface;
import com.blessing333.springbasic.voucher.ui.exception.CommandNotSupportedException;
import com.blessing333.springbasic.voucher.validator.VoucherFormValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

@Component
@RequiredArgsConstructor
@Slf4j
public class VoucherManagingService implements RunnableService {
    private final VoucherFormValidator validator;
    private final VoucherService voucherService;
    private final VoucherManagerServiceUserInterface userInterface;
    private final Converter converter;

    @Override
    public void startService() {
        boolean isExit = false;
        while(!isExit){
            userInterface.showGuideText();
            String command = userInterface.inputMessage();
            try {
                VoucherServiceCommandOptionType type = VoucherServiceCommandOptionType.find(command);
                switch (type) {
                    case CREATE -> startCreateNewVoucher();
                    case LIST -> voucherService.showVoucherList();
                    case QUIT -> isExit = true;
                    default -> userInterface.showHelpText();
                }
            } catch (CommandNotSupportedException e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                log.error(sw.toString());
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
