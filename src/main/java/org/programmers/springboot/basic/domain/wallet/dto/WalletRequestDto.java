package org.programmers.springboot.basic.domain.wallet.dto;

import lombok.Builder;
import lombok.Getter;
import org.programmers.springboot.basic.domain.customer.entity.vo.Email;

import java.util.UUID;

@Builder
@Getter
public class WalletRequestDto {

    private final Email email;
    private final UUID voucherId;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
