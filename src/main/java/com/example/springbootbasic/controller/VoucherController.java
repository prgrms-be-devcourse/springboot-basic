package com.example.springbootbasic.controller;

import com.example.springbootbasic.console.input.RequestBody;
import com.example.springbootbasic.console.output.ResponseBody;
import com.example.springbootbasic.domain.voucher.VoucherEnum;
import com.example.springbootbasic.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

import static com.example.springbootbasic.console.ConsoleStatus.*;
import static com.example.springbootbasic.console.output.ResponseFailMessage.*;
import static com.example.springbootbasic.domain.voucher.VoucherEnum.findVoucherBy;
import static com.example.springbootbasic.domain.voucher.VoucherMessage.CREATE;
import static com.example.springbootbasic.domain.voucher.VoucherMessage.MENU;
import static com.example.springbootbasic.util.CharacterUnit.EMPTY;
import static com.example.springbootbasic.util.CharacterUnit.SPACE;
import static java.lang.Character.isDigit;

@Component
public class VoucherController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
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

        logger.debug("[{}] - selectVoucherMenu",
                responseBody.getStatus());
        return responseBody;
    }

    public ResponseBody selectHowToCreateVoucher() {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setBody(CREATE.getMessage());

        logger.debug("[{}] - selectHowToCreateVoucher",
                responseBody.getStatus());
        return responseBody;
    }

    public ResponseBody createVoucher(RequestBody request) {
        ResponseBody responseBody = new ResponseBody();
        handleCreateVoucher(request, responseBody);

        if (request.getStatus() == FAIL) {
            logger.warn("[{}] - createVoucher request => '{}'",
                    request.getStatus(),
                    request.getBody());
        }
        if (responseBody.getStatus() == FAIL) {
            logger.warn("[{}] - createVoucher response => '{}'",
                    responseBody.getStatus(),
                    responseBody.getBody());
        }
        return responseBody;
    }

    private void handleCreateVoucher(RequestBody request, ResponseBody responseBody) {
        String[] voucherInputForm;
        String voucherType;
        Long discountValue = null;
        Optional<VoucherEnum> findVoucher = Optional.empty();
        if (request.getStatus() == FAIL) {
            responseBody.setBody(VOUCHER_REQUEST_FAIL_ERROR.getMessage());
            responseBody.setStatus(FAIL);
        }
        voucherInputForm = request.getBody().split(SPACE.getUnit());
        if (!validateVoucherInputForm(voucherInputForm)) {
            request.setStatus(FAIL);
            responseBody.setStatus(FAIL);
            responseBody.setBody(VOUCHER_REQUEST_FAIL_ERROR.getMessage());
        }
        if (request.getStatus() == SUCCESS) {
            voucherType = voucherInputForm[VOUCHER_TYPE_INDEX];
            discountValue = Long.parseLong(voucherInputForm[VOUCHER_DISCOUNT_VALUE_INDEX]);
            findVoucher = findVoucherBy(voucherType);
            if (findVoucher.isEmpty()) {
                responseBody.setBody(VOUCHER_FIND_EMPTY_ERROR.getMessage());
                responseBody.setStatus(FAIL);
            }
        }
        if (responseBody.getStatus() == SUCCESS) {
            responseBody.setBody(findVoucher.get().getVoucherType() + discountValue.toString());
            voucherService.saveVoucher(findVoucher.get(), discountValue);
        }

        logger.debug("[{}] - handleCreateVoucher request => '{}'",
                request.getStatus(),
                request.getBody());
        logger.debug("[{}] - handleCreateVoucher response => '{}'",
                responseBody.getStatus(),
                responseBody.getBody());
    }

    private static boolean validateVoucherInputForm(String[] voucherInputForm) {
        return !checkVoucherInputFormLengthNotOk(voucherInputForm) && !checkDiscountValueNotDigit(voucherInputForm);
    }

    private static boolean checkVoucherInputFormLengthNotOk(String[] voucherInputForm) {
        return voucherInputForm.length != 2;
    }

    private static boolean checkDiscountValueNotDigit(String[] voucherInputForm) {
        return Arrays.stream(voucherInputForm[VOUCHER_DISCOUNT_VALUE_INDEX].split(EMPTY.getUnit()))
                .anyMatch(discountValuePiece -> !isDigit(discountValuePiece.charAt(0)));
    }

    public ResponseBody selectAllVouchers() {
        ResponseBody responseBody = new ResponseBody();
        String findAllVouchers = voucherService.findAllVouchers();

        responseBody.setBody(findAllVouchers);
        if (findAllVouchers.isBlank()) {
            responseBody.setBody(VOUCHER_EMPTY_LIST_ERROR.getMessage());
        }

        logger.debug("[{}] - selectAllVouchers",
                responseBody.getStatus());
        return responseBody;
    }

    public ResponseBody shutdownVoucherApplication() {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setStatus(END);

        logger.debug("[{}] - shutdownVoucherApplication",
                responseBody.getStatus());
        return responseBody;
    }
}