package org.prgrms.vouchermanager.domain.customer.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

@Getter
@EqualsAndHashCode(of = "id", doNotUseGetters = true)
public class Customer {

    /* 아이디 */
    private UUID id;

    /* 이름 */
    private final String name;

    /* 이메일 */
    private final String email;

    /* 생성 일시 */
    private final LocalDateTime createAt = LocalDateTime.now();

    /* 상태 */
    private CustomerStatus status = CustomerStatus.CREATED;

    private Customer(String name, String email) {
        validateCustomerConstructorArgs(name, email);

        this.name = name;
        this.email = email;
    }

    public static Customer create(String name, String email) {
        return new Customer(name, email).withId(UUID.randomUUID());
    }

    public static Customer bind(UUID id, String name, String email) {
        return new Customer(name, email).withId(id);
    }

    private Customer withId(UUID id) {
        if (Objects.isNull(id)) throw new IllegalArgumentException("아이디 필수");

        this.id = id;

        return this;
    }

    private void validateCustomerConstructorArgs(String name, String email) {
        validateName(name);
        validateEmail(email);
    }

    private void validateName(String name) {
        checkArgument(Strings.isNotBlank(name), "name은 공백이 될 수 없습니다.");
    }

    private void validateEmail(String email) {
        checkArgument(Strings.isNotBlank(email), "email은 공백이 될 수 없습니다.");
        checkArgument(email.contains("@"), "email은 @를 포함해야 합니다.");
    }

    public void changeBlackList() {
        checkState(this.status == CustomerStatus.CREATED, "블랙리스트로 변경 불가한 상태");

        this.status = CustomerStatus.BLOCKED;
    }
}
