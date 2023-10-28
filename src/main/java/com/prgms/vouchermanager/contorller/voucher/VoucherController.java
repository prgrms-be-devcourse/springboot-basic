package com.prgms.vouchermanager.contorller.voucher;

import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.dto.CreateVoucherDto;
import com.prgms.vouchermanager.dto.UpdateVoucherDto;
import com.prgms.vouchermanager.service.voucher.VoucherService;
import com.prgms.vouchermanager.util.io.ConsoleInput;
import com.prgms.vouchermanager.util.io.ConsoleOutput;
import com.prgms.vouchermanager.validation.InputValidation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

import static com.prgms.vouchermanager.exception.ExceptionType.INVALID_VOUCHER_PERCENT;

@Slf4j
@Controller
public class VoucherController {

    private final VoucherService voucherService;

    private final ConsoleInput consoleInput;

    private final ConsoleOutput consoleOutput;

    private final InputValidation inputValidation;

    public VoucherController(VoucherService voucherService, ConsoleInput consoleInput, ConsoleOutput consoleOutput, InputValidation inputValidation) {
        this.voucherService = voucherService;
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.inputValidation = inputValidation;
    }

    public void create() {
        try {
            consoleOutput.printVoucherType();
            int voucherType = consoleInput.inputVoucherType();
            consoleOutput.printVoucherAmount();
            long value = consoleInput.inputVoucherValue();
            if (voucherType==2&&!inputValidation.validVoucherPercent(value)) {
                throw new RuntimeException(INVALID_VOUCHER_PERCENT.getMessage());
            }
            Voucher voucher = voucherService.create(new CreateVoucherDto(value, voucherType));
            log.info(voucher.toString());

        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }

    }

    public void update() {
        try {
            consoleOutput.printVoucherId();
            UUID id = consoleInput.inputVoucherId();
            consoleOutput.printVoucherType();
            int voucherType = consoleInput.inputVoucherType();
            consoleOutput.printVoucherAmount();
            long value = consoleInput.inputVoucherValue();
            voucherService.update(id, new UpdateVoucherDto(value, voucherType));

        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }
    }

    public void getList() {

        List<Voucher> voucherList = voucherService.findAll();

        voucherList.forEach(voucher -> System.out.println(voucher.toString()));

    }

    public void findById() {

        consoleOutput.printVoucherId();

        try {
            UUID id = consoleInput.inputVoucherId();
            Voucher voucher = voucherService.findById(id);
            log.info(voucher.toString());

        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }

    }

    public void deleteById() {
        consoleOutput.printVoucherId();
        try {
            UUID id = consoleInput.inputVoucherId();
            voucherService.deleteById(id);
            consoleOutput.printSuccessDelete();

        } catch (RuntimeException e) {
            log.warn(e.getMessage());
        }
    }

    public void deleteAll() {
        voucherService.deleteAll();
    }
}
