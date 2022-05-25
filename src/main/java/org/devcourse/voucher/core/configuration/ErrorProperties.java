package org.devcourse.voucher.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:error.yaml", factory = YamlPropertiesFactory.class)
@ConfigurationProperties(prefix = "error")
public class ErrorProperties {

    private static String invalidCommand;
    private static String inputNegativeNumbers;
    private static String inputNotNumbers;
    private static String invalidType;
    private static String notFoundVoucher;

    public static String getNotFoundVoucher() {
        return notFoundVoucher;
    }

    public static String getInputNegativeNumbers() {
        return inputNegativeNumbers;
    }

    public static String getInputNotNumbers() {
        return inputNotNumbers;
    }

    public static String getInvalidCommand() {
        return invalidCommand;
    }

    public static String getInvalidType() {
        return invalidType;
    }

    public void setInputNegativeNumbers(String inputNegativeNumbers) {
        this.inputNegativeNumbers = inputNegativeNumbers;
    }

    public void setInputNotNumbers(String inputNotNumbers) {
        this.inputNotNumbers = inputNotNumbers;
    }

    public void setInvalidCommand(String invalidCommand) {
        this.invalidCommand = invalidCommand;
    }

    public void setInvalidType(String invalidType) {
        this.invalidType = invalidType;
    }

    public void setNotFoundVoucher(String notFoundVoucher) { this.notFoundVoucher = notFoundVoucher; }
}
