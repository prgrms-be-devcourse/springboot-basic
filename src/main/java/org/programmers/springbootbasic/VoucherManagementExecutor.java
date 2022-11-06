package org.programmers.springbootbasic;

import lombok.AllArgsConstructor;
import org.programmers.springbootbasic.data.VoucherMainMenuCommand;
import org.programmers.springbootbasic.domain.Voucher;
import org.programmers.springbootbasic.dto.VoucherDto;
import org.programmers.springbootbasic.dto.VoucherDtoConverter;
import org.programmers.springbootbasic.dto.VoucherInputDto;
import org.programmers.springbootbasic.exception.WrongInputException;
import org.programmers.springbootbasic.io.Input;
import org.programmers.springbootbasic.io.Output;
import org.programmers.springbootbasic.service.VoucherService;
import org.programmers.springbootbasic.util.ConstantMessageUtil;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class VoucherManagementExecutor {

    private final Input input;
    private final Output output;
    private final VoucherDtoConverter voucherDtoConverter;
    private final VoucherService voucherService;


    public void run() throws IOException {

        while(true) {
            output.printMenu(ConstantMessageUtil.VOUCHER_MAIN_MENU);
            VoucherMainMenuCommand menuInput = input.getVoucherMainMenuInput(ConstantMessageUtil.TYPE_USER_COMMAND);
            switch (menuInput) {
                case CREATE -> {
                    createVoucher();
                }
                case LIST -> {
                    List<Voucher> vouchers = voucherService.lookupVoucher();
                    output.printVouchers(vouchers);
                }
                case EXIT -> {
                    /*
                    return(?)
                     */
                }
                default -> {
                    output.printError();
                }
            }
        }
    }

    private void createVoucher() {
        boolean continueJob = true;
        while(continueJob) {
            try {
                VoucherInputDto voucherInputDto = input.getVoucherCreateMenuInput(ConstantMessageUtil.TYPE_VOUCHER_INFO);
                voucherInputDto.validateVoucher();
                VoucherDto voucherDto = voucherDtoConverter.convertVoucherInput(voucherInputDto);
                voucherService.createVoucher(voucherDto);
            } catch(WrongInputException | IOException | NumberFormatException e) {
                output.printError();
                continue;
            }
            continueJob = false;
        }
    }
}
