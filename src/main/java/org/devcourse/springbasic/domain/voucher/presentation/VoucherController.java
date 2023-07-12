package org.devcourse.springbasic.domain.voucher.presentation;

import lombok.RequiredArgsConstructor;
import org.devcourse.springbasic.domain.voucher.domain.VoucherMenuType;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.devcourse.springbasic.domain.voucher.dto.VoucherDto;
import org.devcourse.springbasic.domain.voucher.service.VoucherService;
import org.devcourse.springbasic.global.io.ErrorMsgPrinter;
import org.devcourse.springbasic.global.io.input.voucher.VoucherInput;
import org.devcourse.springbasic.global.io.output.voucher.VoucherOutput;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    public void run(VoucherInput voucherInput, VoucherOutput voucherOutput) {
        VoucherMenuType menu = VoucherMenuType.EXIT;
        do {
            try {
                voucherOutput.menus();
                menu = voucherInput.menu();

                switch (menu) {
                    case CREATE:
                        createVoucher(voucherInput, voucherOutput);
                    case LIST:
                        getHistory(voucherOutput);
                }
            } catch (IllegalArgumentException e) {
                ErrorMsgPrinter.print(e.getMessage());
            }
        } while (!menu.isExit());
    }

    private void createVoucher(VoucherInput voucherInput, VoucherOutput voucherOutput) {
        voucherOutput.voucherMenus();
        VoucherType voucherType = voucherInput.voucherMenu();
        VoucherDto.SaveRequestDto voucherDto = new VoucherDto.SaveRequestDto(voucherType);
        voucherService.save(voucherDto);
    }

    private void getHistory(VoucherOutput voucherOutput) {
        List<VoucherDto.ResponseDto> vouchers = voucherService.findAll();
        voucherOutput.vouchers(vouchers);
    }
}
