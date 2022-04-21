package com.blessing333.springbasic.voucher;

import com.blessing333.springbasic.RunnableService;
import com.blessing333.springbasic.common.ui.CommandNotSupportedException;
import com.blessing333.springbasic.common.util.ExceptionStackTraceConverter;
import com.blessing333.springbasic.voucher.converter.Converter;
import com.blessing333.springbasic.voucher.domain.Voucher;
import com.blessing333.springbasic.voucher.dto.ConvertedVoucherCreateForm;
import com.blessing333.springbasic.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.voucher.exception.VoucherCreateFailException;
import com.blessing333.springbasic.voucher.service.VoucherService;
import com.blessing333.springbasic.voucher.ui.VoucherManagerUserInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class VoucherManagingService implements RunnableService {
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
                VoucherServiceCommandOptionType type = VoucherServiceCommandOptionType.find(command);
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
