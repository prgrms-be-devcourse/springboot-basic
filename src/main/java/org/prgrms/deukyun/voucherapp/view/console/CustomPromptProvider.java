package org.prgrms.deukyun.voucherapp.view.console;

import org.jline.utils.AttributedString;
import org.prgrms.deukyun.voucherapp.system.security.CustomerHolder;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

/**
 * 프롬프트 제공자 - 메인 메뉴 메시지를 제공함
 */
@Component
public class CustomPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        String customerName = CustomerHolder.getCustomer().getName();

        String greetingMessage = String.format(
                "\n안녕하세요 %s 님" +
                "\n=== Voucher Program ===" +
                "\nType exit to exit the program." +
                "\nType create to create a new voucher." +
                "\nType list to list all vouchers.\n\n",
                customerName);
        return new AttributedString(greetingMessage);
    }
}
