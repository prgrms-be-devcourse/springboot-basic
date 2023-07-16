package prgms.spring_week1.domain.customer.model.embeddedType;

import org.springframework.util.Assert;

import java.util.regex.Pattern;

public class Email {
    private final String address;

    public Email(String address) {
        Assert.notNull(address, "이메일은 필수 입력값입니다.");
        Assert.isTrue(checkAddressLength(address), "이메일은 4자 이상 50자 이하여야합니다.");
        Assert.isTrue(checkAddressFormat(address), "이메일 형식이 올바르지 않습니다.");
        this.address = address;
    }

    private boolean checkAddressLength(String address) {
        return address.length() >= 4 && address.length() <= 50;
    }

    private boolean checkAddressFormat(String address) {
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address);
    }

    public String getAddress() {
        return address;
    }
}
