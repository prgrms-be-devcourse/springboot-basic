package com.prgms.management.voucher.model;

import com.prgms.management.common.exception.InvalidParameterException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
public abstract class Voucher implements VoucherPolicy, CSVPolicy {
    private final UUID id;
    private final Timestamp createdAt;
    private final Integer figure;
    private final VoucherType type;
    private String name;

    protected Voucher(UUID id, String name, Integer figure, Timestamp createdAt, VoucherType type, Integer MAX,
                      Integer MIN) {
        if (figure < MIN || figure > MAX) {
            throw new InvalidParameterException(MIN + "과 " + MAX + "사이의 값을 입력해주세요.");
        }
        this.id = id;
        this.createdAt = createdAt;
        this.figure = figure;
        this.type = type;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voucher voucher = (Voucher) o;
        return Objects.equals(id, voucher.id) && Objects.equals(figure, voucher.figure) && type == voucher.type && Objects.equals(name, voucher.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, figure, type, name);
    }
}
