package org.prgms.customer;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

public record Customer(

        /* 고객 아이디 */
        UUID customerId,

        /* 고객 이름 */
        String name,

        /* 고객 이메일 */
        String email

) {

    public Customer {
        checkArgument(customerId != null, "고객 id는 null 일 수 없습니다.");

        checkArgument(name != null, "이름은 null 일 수 없습니다.");
        checkArgument(!name.isBlank(), "이름은 비어 있을 수 없습니다.");

        checkArgument(email != null, "이메일은 null 일 수 없습니다.");
        checkArgument(!email.isBlank(), "이메일은 비어 있을 수 없습니다.");
    }
    
    public Customer(String name, String email) {
        this(UUID.randomUUID(), name, email);
    }
}
