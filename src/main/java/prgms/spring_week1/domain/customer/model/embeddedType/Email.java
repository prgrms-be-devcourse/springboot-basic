package prgms.spring_week1.domain.customer.model.embeddedType;

import java.util.regex.Pattern;

public class Email {
    private final int MINIMUM_EMAIL_LENGTH = 4;
    private final int MAXIMUM_EMAIL_LENGTH = 50;
    private final String EMAIL_FORMAT_CHECK_REGEX = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";

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
        boolean isInvalidEmailLength = !(MINIMUM_EMAIL_LENGTH < address.length() && address.length() < MAXIMUM_EMAIL_LENGTH);
        
        if (isInvalidEmailLength) {
            throw new IllegalArgumentException("이메일은 4자 이상 50자 이하여야합니다.");
        }
    }

    private void checkAddressFormat(String address) {
        boolean isInvalidEmailFormat = !(Pattern.matches(EMAIL_FORMAT_CHECK_REGEX, address));

        if (isInvalidEmailFormat) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }

    public String getAddress() {
        return address;
    }
}
