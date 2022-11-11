package com.example.springbootbasic.console.input;

import com.example.springbootbasic.console.ConsoleType;
import com.example.springbootbasic.controller.request.RequestBody;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.example.springbootbasic.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.springbootbasic.console.ConsoleType.EXIT;
import static com.example.springbootbasic.console.ConsoleType.findConsoleTypeBy;
import static com.example.springbootbasic.util.CharacterUnit.SPACE;

@Component
public class ConsoleInput {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleInput.class);
    private static final int VOUCHER_TYPE_INDEX = 0;
    private static final int VOUCHER_DISCOUNT_VALUE_INDEX = 1;

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public ConsoleType inputCommand() {
        ConsoleType voucherExit = EXIT;
        try {
            voucherExit = findConsoleTypeBy(br.readLine()).get();
        } catch (IOException e) {
            logger.error("");
        }
        return voucherExit;
    }

    public RequestBody<VoucherDto> inputVoucherData() {
        VoucherDto voucherDto = new VoucherDto();
        try {
            String[] voucherPieces = br.readLine().split(SPACE.unit());
            VoucherType voucherType = VoucherType.findVoucherType(voucherPieces[VOUCHER_TYPE_INDEX]);
            long discountValue = Long.parseLong(voucherPieces[VOUCHER_DISCOUNT_VALUE_INDEX]);
            voucherDto = new VoucherDto(discountValue, voucherType);
        } catch (IOException e) {
            logger.error("");
            return RequestBody.fail(voucherDto);
        }
        return RequestBody.success(voucherDto);
    }
}