package com.example.springbootbasic.console.output;

import com.example.springbootbasic.controller.response.ResponseBody;
import com.example.springbootbasic.dto.CustomerDto;
import com.example.springbootbasic.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.List;

import static com.example.springbootbasic.console.message.ConsoleOutputMessage.SAVE_VOUCHER_SUCCESS;
import static com.example.springbootbasic.util.CharacterUnit.ENTER;

@Component
public class ConsoleOutput {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutput.class);

    private final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

    public void printConsole(String text) {
        try {
            bw.write(text);
            bw.flush();
        } catch (IOException e) {
            logger.error("Fail - {}", e.getMessage());
        }
    }

    public void printSaveVoucher(ResponseBody<VoucherDto> responseBody) {
        VoucherDto voucherDto = responseBody.getData();
        try {
            bw.write(MessageFormat.format("{0} {1} {2} {3} {4} {5}",
                    SAVE_VOUCHER_SUCCESS.message(), ENTER.unit(), voucherDto.getVoucherId(), voucherDto.getVoucherType(), voucherDto.getDiscountValue(), ENTER.unit()));
            bw.flush();
        } catch (IOException e) {
            logger.error("Fail - {}", e.getMessage());
        }
    }

    public void printVouchers(ResponseBody<List<VoucherDto>> responseBody) {
        List<VoucherDto> voucherDtos = responseBody.getData();
        voucherDtos.forEach(voucherDto -> {
            try {
                bw.write(MessageFormat.format("{0} {1} {2} {3}", voucherDto.getVoucherId(), voucherDto.getVoucherType(), voucherDto.getDiscountValue(), ENTER.unit()));
            } catch (IOException e) {
                logger.error("Fail - {}", e.getMessage());
            }
        });
    }

    public void printCustomers(ResponseBody<List<CustomerDto>> responseBody) {
        List<CustomerDto> customerDtos = responseBody.getData();
        customerDtos.forEach(customerDto -> {
            try {
                bw.write(MessageFormat.format("{0} {1} {2}", customerDto.getCustomerId(), customerDto.getStatus(), ENTER.unit()));
            } catch (IOException e) {
                logger.error("Fail - {}", e.getMessage());
            }
        });
    }
}