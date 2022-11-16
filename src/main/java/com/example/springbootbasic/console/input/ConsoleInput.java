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
import java.util.NoSuchElementException;

import static com.example.springbootbasic.console.ConsoleType.EXIT;
import static com.example.springbootbasic.console.ConsoleType.of;
import static com.example.springbootbasic.util.CharacterUnit.SPACE;

@Component
public class ConsoleInput {

    private static final Logger logger = LoggerFactory.getLogger(ConsoleInput.class);
    private static final int VOUCHER_TYPE_INDEX = 0;
    private static final int VOUCHER_DISCOUNT_VALUE_INDEX = 1;

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public ConsoleType inputCommand() {
        ConsoleType voucherExit = ConsoleType.CONTINUE;
        try {
            voucherExit = ConsoleType.of(br.readLine());
        } catch (IOException | IllegalArgumentException e) {
            logger.error("Fail - {}", e.getMessage());
        }
        return voucherExit;
    }

    public RequestBody<VoucherDto> inputVoucherData() {
        VoucherDto voucherDto = new VoucherDto();
        try {
            String[] voucherPieces = br.readLine().split(SPACE.unit());
            VoucherType voucherType = VoucherType.findVoucherType(voucherPieces[VOUCHER_TYPE_INDEX]);
            long discountValue = Long.parseLong(voucherPieces[VOUCHER_DISCOUNT_VALUE_INDEX]);
            return RequestBody.success(new VoucherDto(discountValue, voucherType));
        } catch (IOException | IllegalArgumentException e) {
            logger.error("Fail - {}", e.getMessage());
            return RequestBody.fail(voucherDto);
        }
    }
}