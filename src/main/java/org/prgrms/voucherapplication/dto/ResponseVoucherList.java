package org.prgrms.voucherapplication.dto;

import org.prgrms.voucherapplication.entity.Voucher;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseVoucherList {

    private final List<Voucher> list;

    public ResponseVoucherList(List<Voucher> list) {
        this.list = list;
    }

    public void add(Voucher voucher) {
        list.add(voucher);
    }

    public String findAll() {
        return list.stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}
