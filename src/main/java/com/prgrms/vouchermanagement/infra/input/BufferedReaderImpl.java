package com.prgrms.vouchermanagement.infra.input;

import com.prgrms.vouchermanagement.infra.exception.InvalidFormatException;
import com.prgrms.vouchermanagement.infra.utils.MenuPatternUtils;
import com.prgrms.vouchermanagement.infra.utils.MenuType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class BufferedReaderImpl implements InputProvider {

    private final BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(System.in));
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public MenuType inputMenuType() throws IOException {
        while(true) {
            String menuTypeString = bufferedReader.readLine();
            try {
                if (!MenuPatternUtils.MENU.matcher(menuTypeString).matches()) {
                    throw new InvalidFormatException("input type은 [list, create, exit] 중 하나이어야 합니다.\n");
                }
                return MenuType.getType(menuTypeString);
            } catch (InvalidFormatException e) {
                logger.error(e.getMessage());
            }

        }
    }

    @Override
    public String inputVoucherType() throws IOException {
        while(true) {
            String voucherTypeString = bufferedReader.readLine();
            try {
                if (!MenuPatternUtils.VOUCHER_TYPE.matcher(voucherTypeString).matches()) {
                    throw new InvalidFormatException("input type은 [fixed, rate] 중 하나이어야 합니다.");
                }
                return voucherTypeString;
            } catch (InvalidFormatException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public int inputVoucherAmount() throws IOException {
        while(true) {
            String amountString = bufferedReader.readLine();
            try {
                if(!MenuPatternUtils.VOUCHER_AMOUNT.matcher(amountString).matches()) {
                    throw new InvalidFormatException("input 값은 양의 정수이어야 합니다.");
                }
                return Integer.parseInt(amountString);
            } catch (InvalidFormatException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public String inputVoucherName() throws IOException {
        return bufferedReader.readLine();
    }
}
