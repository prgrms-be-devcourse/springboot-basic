package prgms.spring_week1.domain.customer.model.embeddedType;

import org.springframework.util.Assert;

import java.util.regex.Pattern;

public class Email {
    private final int minimumEmailLength = 4;
    private final int maximumEmailLength = 50;
    private final String emailFormatCheckRegex = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";

    private final String address;

    public Email(String address) {
        checkAddress(address);
        this.address = address;
    }

    private void checkAddress(String address){
        checkAddressIsNull(address);
        checkAddressLength(address);
        checkAddressFormat(address);
    }

    private void checkAddressIsNull(String address) {
        boolean addressIsNull = address == null;

        if (addressIsNull) {
            throw new IllegalArgumentException("이메일은 필수 입력값입니다.");
        }
    }

    private void checkAddressLength(String address) {
        boolean isInvalidEmailLength = (address.length() < minimumEmailLength || address.length() > maximumEmailLength);
        if (isInvalidEmailLength) {
            throw new IllegalArgumentException("이메일은 4자 이상 50자 이하여야합니다.");
        }
    }

    private void checkAddressFormat(String address) {
        boolean isInvalidEmailFormat = !(Pattern.matches(emailFormatCheckRegex, address));

        if (isInvalidEmailFormat) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }

    public String getAddress() {
        return address;
    }
}
