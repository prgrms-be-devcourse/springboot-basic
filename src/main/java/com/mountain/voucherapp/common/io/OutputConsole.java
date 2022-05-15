package com.mountain.voucherapp.common.io;

import com.mountain.voucherapp.dto.CustomerDto;
import com.mountain.voucherapp.model.VoucherEntity;
import com.mountain.voucherapp.model.enums.DiscountPolicy;
import com.mountain.voucherapp.model.enums.Menu;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import static com.mountain.voucherapp.common.constants.ProgramMessage.MANUAL_TITLE;

@Component
public class OutputConsole implements Output {

    public static final TextIO textIO = TextIoFactory.getTextIO();
    public static final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    private static final String COMMON_SEQ_FORMAT = "[{0}]. {1}";
    private static final String VOUCHER_LIST_PRINT_FORMAT = "[{0}]. {1}: {2}{3}";
    private static final String PRINT_CUSTOMER_VOUCHER_INFO_FORMAT = "[{0}]. email : {1}, voucherId : {2}";
    private static final String PRINT_EXCEPTION_FORMAT = "[{0}]: {1}";

    @Override
    public void close() {
        textTerminal.abort();
    }

    @Override
    public void printMessage(String msg) {
        textTerminal.println(msg);
    }

    @Override
    public void printManual() {
        textTerminal.println(MANUAL_TITLE);
        for (Integer key : Menu.menuMap.keySet()) {
            Menu menu = Menu.find(key).get();
            textTerminal.println(MessageFormat.format(COMMON_SEQ_FORMAT, menu.getSeq(), menu.getDescription()));
        }
    }

    @Override
    public void choiceDiscountPolicy() {
        Arrays.stream(DiscountPolicy.values())
                .forEach(
                        (discountPolicy) -> textTerminal.println(MessageFormat.format(
                                COMMON_SEQ_FORMAT,
                                discountPolicy.getSeq(),
                                discountPolicy.getDescription())
                        )
                );
    }

    @Override
    public void printVoucherList(List<VoucherEntity> voucherEntityList) {
        for (int i = 0; i < voucherEntityList.size(); i++) {
            VoucherEntity voucherEntity = voucherEntityList.get(i);
            textTerminal.println(MessageFormat.format(VOUCHER_LIST_PRINT_FORMAT,
                    i,
                    voucherEntity.getDiscountPolicy().getDescription(),
                    voucherEntity.getDiscountAmount(),
                    voucherEntity.getDiscountPolicy().getUnit()));
        }

    }

    @Override
    public void printCustomerList(List<CustomerDto> customerDtos) {
        for (int i = 0; i < customerDtos.size(); i++) {
            CustomerDto customerDto = customerDtos.get(i);
            textTerminal.println(MessageFormat.format(COMMON_SEQ_FORMAT,
                    i,
                    customerDto.getEmail()));
        }
    }

    @Override
    public void printCustomerVoucherInfo(List<CustomerDto> customerDtos) {
        for (int i = 0; i < customerDtos.size(); i++) {
            CustomerDto customerDto = customerDtos.get(i);
            textTerminal.println(MessageFormat.format(PRINT_CUSTOMER_VOUCHER_INFO_FORMAT,
                    i,
                    customerDto.getEmail(),
                    customerDto.getVoucherId()));
        }
    }

    @Override
    public void printException(Exception e) {
        textTerminal.println(MessageFormat.format(PRINT_EXCEPTION_FORMAT,
                e.getClass().getCanonicalName(),
                e.getMessage()));
    }

}
