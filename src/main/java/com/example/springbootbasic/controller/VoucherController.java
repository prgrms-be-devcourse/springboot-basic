package com.example.springbootbasic.controller;

import com.example.springbootbasic.console.input.RequestBody;
import com.example.springbootbasic.console.output.ResponseBody;
import com.example.springbootbasic.domain.voucher.VoucherEnum;
import com.example.springbootbasic.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.example.springbootbasic.console.ConsoleStatus.END;
import static com.example.springbootbasic.console.ConsoleStatus.FAIL;
import static com.example.springbootbasic.domain.voucher.VoucherEnum.findVoucherGenerator;
import static com.example.springbootbasic.domain.voucher.VoucherMessage.CREATE;
import static com.example.springbootbasic.domain.voucher.VoucherMessage.MENU;
import static com.example.springbootbasic.util.CharacterUnit.SPACE;

@Component
public class VoucherController {
    private static final int VOUCHER_TYPE_INDEX = 0;
    private static final int VOUCHER_DISCOUNT_VALUE_INDEX = 1;
    private final VoucherService voucherService;

    @Autowired
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public ResponseBody selectVoucherMenu() {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setBody(MENU.getMessage());
        return responseBody;
    }

    public ResponseBody selectHowToCreateVoucher() {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setBody(CREATE.getMessage());
        return responseBody;
    }

    public ResponseBody createVoucher(RequestBody request) {
        ResponseBody responseBody = new ResponseBody();
        String[] voucherInputForm = request.getBody().split(SPACE.getUnit());
        String voucherType = voucherInputForm[VOUCHER_TYPE_INDEX];
        Long discountValue = Long.parseLong(voucherInputForm[VOUCHER_DISCOUNT_VALUE_INDEX]);
        Optional<VoucherEnum> findVoucherGenerator =
                findVoucherGenerator(voucherType);

        if (findVoucherGenerator.isEmpty()) {
            responseBody.setStatus(FAIL);
            return responseBody;
        }
        voucherService.saveVoucher(findVoucherGenerator.get(), discountValue);
        return responseBody;
    }

    public ResponseBody selectAllVouchers() {
        ResponseBody responseBody = new ResponseBody();
        String findAllVouchers = voucherService.findAllVouchers();

        if (findAllVouchers.isBlank()) {
            responseBody.setStatus(FAIL);
            return responseBody;
        }
        responseBody.setBody(findAllVouchers);
        return responseBody;
    }

    public ResponseBody shutdownVoucherApplication() {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatus(END);
        return responseBody;
    }
}
