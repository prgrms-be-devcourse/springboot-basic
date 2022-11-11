package org.prgrms.voucherapplication.dto;

import org.prgrms.voucherapplication.entity.Voucher;

import java.util.ArrayList;

public class ResponseVoucherList {

    private ArrayList<Voucher> list = new ArrayList<>();

    // 콘솔에 띄워줄 내용 toString() 구현
    @Override
    public String toString() {
        return "ResponseVoucherList{" +
                "list=" + list +
                '}';
    }
}
