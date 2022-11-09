package com.example.springbootbasic.controller;

import com.example.springbootbasic.console.console.ConsoleType;
import com.example.springbootbasic.console.input.RequestBody;
import com.example.springbootbasic.console.output.ResponseBody;
import com.example.springbootbasic.domain.voucher.VoucherEnum;
import com.example.springbootbasic.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static com.example.springbootbasic.console.ResponseType.*;
import static com.example.springbootbasic.console.console.ConsoleType.VOUCHER_CREATE;
import static com.example.springbootbasic.domain.voucher.VoucherMessage.CREATE;
import static com.example.springbootbasic.domain.voucher.VoucherMessage.MENU;
import static com.example.springbootbasic.util.CharacterUnit.SPACE;

@Controller
public class VoucherController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private static final int VOUCHER_TYPE_INDEX = 0;
    private static final int VOUCHER_DISCOUNT_VALUE_INDEX = 1;

    private final VoucherService voucherService;

    @Autowired
    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public ResponseBody request(RequestBody requestBody) {
        ResponseBody responseBody = new ResponseBody();
        try {
            ConsoleType consoleType = requestBody.getConsoleType();
            switch (consoleType) {
                case VOUCHER_MENU -> responseBody = responseMenu();
                case VOUCHER_CREATE_MENU -> responseBody = responseHowToSaveVoucher();
                case VOUCHER_CREATE -> responseBody = responseSaveVoucher(requestBody);
                case VOUCHER_LIST -> responseBody = responseAllVouchers();
                case VOUCHER_EXIT -> responseBody = responseExit();
                default -> responseBody.setStatus(FAIL);
            }
        } catch (Exception e) {
            logger.error("[{}] request {}", requestBody.getStatus(), requestBody.getConsoleType());
            logger.error("[{}] response {}", responseBody.getStatus(), responseBody.getConsoleType());
        }
        return responseBody;
    }

    private ResponseBody responseMenu() {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatus(SUCCESS);
        responseBody.setBody(MENU.getMessage());
        return responseBody;
    }

    private ResponseBody responseHowToSaveVoucher() {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatus(AGAIN);
        responseBody.setBody(CREATE.getMessage());
        responseBody.setConsoleType(VOUCHER_CREATE);
        return responseBody;
    }

    private ResponseBody responseSaveVoucher(RequestBody requestBody) {
        ResponseBody responseBody = new ResponseBody();
        String[] voucherPieces = requestBody.getBody().split(SPACE.getUnit());
        String voucherType = voucherPieces[VOUCHER_TYPE_INDEX];
        long discountValue = Long.parseLong(voucherPieces[VOUCHER_DISCOUNT_VALUE_INDEX]);
        Optional<VoucherEnum> findVoucher = VoucherEnum.findVoucherBy(voucherType);

        if (findVoucher.isEmpty()) {
            responseBody.setStatus(FAIL);
            logger.error("[{}] request {} - VoucherEnum Null - {}",
                    requestBody.getStatus(),
                    requestBody.getConsoleType(),
                    requestBody.getBody());
        }
        if (findVoucher.isPresent()) {
            responseBody.setStatus(SUCCESS);
            voucherService.saveVoucher(findVoucher.get(), discountValue);
        }
        return responseBody;
    }

    private ResponseBody responseAllVouchers() {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatus(SUCCESS);
        responseBody.setBody(voucherService.findAllVouchers());
        return responseBody;
    }

    private ResponseBody responseExit() {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatus(END);
        return responseBody;
    }
}
