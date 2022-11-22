package org.prgrms.springbootbasic;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "notification")
public class NotificationProperties {
    private String voucherPrompt;
    private String customerPrompt;
    private String wrongInput;
    private String voucherTypeChoice;
    private String exit;

    public String getVoucherPrompt() {
        return voucherPrompt;
    }

    public String getCustomerPrompt() {
        return customerPrompt;
    }

    public String getWrongInput() {
        return wrongInput;
    }

    public String getVoucherTypeChoice() {
        return voucherTypeChoice;
    }

    public String getExit() {
        return exit;
    }

    public void setVoucherPrompt(String voucherPrompt) {
        this.voucherPrompt = voucherPrompt;
    }

    public void setCustomerPrompt(String customerPrompt) {
        this.customerPrompt = customerPrompt;
    }

    public void setVoucherTypeChoice(String voucherTypeChoice) {
        this.voucherTypeChoice = voucherTypeChoice;
    }

    public void setWrongInput(String wrongInput) {
        this.wrongInput = wrongInput;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }
}
