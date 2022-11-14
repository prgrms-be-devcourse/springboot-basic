package org.programmers.springbootbasic;

import lombok.AllArgsConstructor;
import org.programmers.springbootbasic.data.VoucherMainMenuCommand;
import org.programmers.springbootbasic.domain.Customer;
import org.programmers.springbootbasic.domain.Voucher;
import org.programmers.springbootbasic.dto.VoucherDto;
import org.programmers.springbootbasic.dto.VoucherDtoConverter;
import org.programmers.springbootbasic.dto.VoucherInputDto;
import org.programmers.springbootbasic.exception.WrongRangeInputException;
import org.programmers.springbootbasic.exception.WrongTypeInputException;
import org.programmers.springbootbasic.io.Input;
import org.programmers.springbootbasic.io.Output;
import org.programmers.springbootbasic.service.CustomerService;
import org.programmers.springbootbasic.service.VoucherService;
import org.programmers.springbootbasic.util.ConstantMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final CustomerService customerService;
    private static Logger logger = LoggerFactory.getLogger(VoucherManagementExecutor.class);


    public void run() throws IOException {
        boolean running = true;
        while (running) {
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
                case BLACKLIST -> {
                    List<Customer> blackList = customerService.loopUpBlackList();
                    output.printBlacklist(blackList);
                }
                case EXIT -> {
                    running = false;
                }
                default -> {
                    output.printError();
                }
            }
        }
    }

    private void createVoucher() {
        boolean continueJob = true;
        while (continueJob) {
            try {
                VoucherInputDto voucherInputDto = input.getVoucherCreateMenuInput(ConstantMessageUtil.TYPE_VOUCHER_INFO);
                // 잘못된 입력값인지 확인
                voucherInputDto.validateVoucher();
                VoucherDto voucherDto = voucherDtoConverter.convertVoucherInput(voucherInputDto);
                voucherService.createVoucher(voucherDto);
            } catch (WrongTypeInputException e) {
                logger.error("잘못된 type 입력입니다.");
                output.printError();
                continue;
            } catch (WrongRangeInputException e) {
                logger.error("잘못된 amount 범위의 입력입니다.");
                output.printError();
                continue;
            } catch (NumberFormatException e) {
                logger.error("잘못된 amount 입력입니다.");
                output.printError();
                continue;
            } catch (IOException e) {
                logger.error("IOException");
                output.printError();
                continue;
            }
            continueJob = false;
        }
    }
}
