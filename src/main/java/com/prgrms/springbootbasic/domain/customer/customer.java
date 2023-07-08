package com.prgrms.springbootbasic.domain.customer;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class customer {

    private final UUID customerId;

    @NonNull
    private String name;

    private final String email;

    private LocalDateTime lastLoginAt;

    private final LocalDateTime createAt;

    //이름 변경
    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("이름란은 비워둘 수 없습니다!");
        }
    }
}
