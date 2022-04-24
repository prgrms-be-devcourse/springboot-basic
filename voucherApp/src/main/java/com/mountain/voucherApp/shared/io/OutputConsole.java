package com.mountain.voucherApp.shared.io;

import com.mountain.voucherApp.application.port.in.CustomerDto;
import com.mountain.voucherApp.shared.enums.DiscountPolicy;
import com.mountain.voucherApp.shared.enums.Menu;
import com.mountain.voucherApp.adapter.out.persistence.voucher.VoucherEntity;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.mountain.voucherApp.shared.constants.Message.MANUAL_TITLE;
import static com.mountain.voucherApp.shared.utils.DiscountPolicyUtil.getDiscountPolicyMap;
import static com.mountain.voucherApp.shared.utils.MenuUtil.getMenuMap;

@Component
public class OutputConsole implements Output {

    public static final TextIO textIO = TextIoFactory.getTextIO();
    public static final TextTerminal<?> textTerminal = textIO.getTextTerminal();

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
        Map<Integer, Menu> menuMap = getMenuMap();
        for (Integer key : menuMap.keySet()) {
            Menu menu = menuMap.get(key);
            textTerminal.println(MessageFormat.format("[{0}]. {1}", menu.ordinal(), menu.getDescription()));
        }
    }

    @Override
    public void choiceDiscountPolicy() {
        Arrays.stream(DiscountPolicy.values())
                .forEach(
                        (p) -> textTerminal.println(MessageFormat.format(
                                "[{0}]. {1}",
                                p.getPolicyId(),
                                p.getDescription())
                        )
                );
    }

    @Override
    public void printVoucherList(List<VoucherEntity> voucherEntityList) {
        Map<Integer, DiscountPolicy> discountPolicyMap = getDiscountPolicyMap();
        for (int i = 0; i < voucherEntityList.size(); i++) {
            VoucherEntity voucherEntity = voucherEntityList.get(i);
            DiscountPolicy policy = discountPolicyMap.get(voucherEntity.getDiscountPolicyId());
            textTerminal.println(MessageFormat.format("[{0}]. {1}: {2}{3}",
                    i,
                    policy.getDescription(),
                    voucherEntity.getDiscountAmount(),
                    policy.getUnit()));
        }

    }

    @Override
    public void printCustomerList(List<CustomerDto> customerDtos) {
        for (int i = 0; i < customerDtos.size(); i++) {
            CustomerDto customerDto = customerDtos.get(i);
            textTerminal.println(MessageFormat.format("[{0}]. {1}",
                    i,
                    customerDto.getEmail()));
        }
    }

    @Override
    public void printCustomerVoucherInfo(List<CustomerDto> customerDtos) {
        for (int i = 0; i < customerDtos.size(); i++) {
            CustomerDto customerDto = customerDtos.get(i);
            textTerminal.println(MessageFormat.format("[{0}]. email : {1}, voucherId : {2}",
                    i,
                    customerDto.getEmail(),
                    customerDto.getVoucherId()));
        }
    }

    @Override
    public void printException(Exception e) {
        textTerminal.println(MessageFormat.format("[{0}]: {1}",
                e.getClass().getCanonicalName(),
                e.getMessage()));
    }

}
