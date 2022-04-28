package org.devcourse.voucher.error;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "error")
public class ErrorProperties {
    private static String invalidCommand;
    private static String inputNegativeNumbers;
    private static String inputNotNumbers;

    public static String getInputNegativeNumbers() {
        return inputNegativeNumbers;
    }

    public void setInputNegativeNumbers(String inputNegativeNumbers) {
        this.inputNegativeNumbers = inputNegativeNumbers;
    }

    public static String getInputNotNumbers() {
        return inputNotNumbers;
    }

    public void setInputNotNumbers(String inputNotNumbers) {
        this.inputNotNumbers = inputNotNumbers;
    }

    public static String getInvalidCommand() {
        return invalidCommand;
    }

    public void setInvalidCommand(String invalidCommand) {
        this.invalidCommand = invalidCommand;
    }
}
