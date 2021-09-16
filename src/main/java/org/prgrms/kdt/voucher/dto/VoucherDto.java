package org.prgrms.kdt.voucher.dto;

import org.prgrms.kdt.customer.domain.vo.Email;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.domain.vo.Type;

import java.time.LocalDateTime;
import java.util.UUID;

public class VoucherDto {
    private Email email;
    private Type type;
    private long value;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}
