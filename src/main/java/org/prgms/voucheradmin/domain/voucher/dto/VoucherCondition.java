package org.prgms.voucheradmin.domain.voucher.dto;

import java.time.LocalDate;

import org.prgms.voucheradmin.domain.voucher.entity.vo.VoucherType;
import org.prgms.voucheradmin.global.exception.customexception.WrongInputException;
import org.springframework.format.annotation.DateTimeFormat;

public class VoucherCondition {
    private VoucherType voucherType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate from;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to;

    public VoucherCondition(VoucherType voucherType, LocalDate from, LocalDate to) {
        this.voucherType = voucherType;

        if(from != null && to == null) {
            throw new WrongInputException("required 'from' date");
        }

        if(from == null && to != null) {
            throw new WrongInputException("required 'to' date");
        }

        if(from != null && to != null) {
            if(from.isAfter(to)) {
                throw new WrongInputException("wrong range date");
            }
        }

        this.from = from;
        this.to = to;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public boolean hasVoucherType() {
        if(voucherType != null) {
            return true;
        }else{
            return false;
        }
    }

    public boolean hasVoucherDateRage() {
        if(from != null && to != null) {
            return true;
        }else{
            return false;
        }
    }

}
