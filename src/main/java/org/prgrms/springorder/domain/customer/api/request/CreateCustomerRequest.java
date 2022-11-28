package org.prgrms.springorder.domain.customer.api.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import org.prgrms.springorder.console.io.Request;
import org.springframework.util.StringUtils;

@Getter
public class CreateCustomerRequest implements Request {

    private final String name;

    private final String email;

    public CreateCustomerRequest(String name, String email) {
        validateName(name);
        validateEmail(email);
        this.name = name;
        this.email = email;
    }

    private void validateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("잘못된 이름입니다. : " + name);
        }
    }

    private void validateEmail(String email) {
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(!m.matches()) {
            throw new IllegalArgumentException("잘못된 이메일입니다. : " + email);
        }
    }

}
