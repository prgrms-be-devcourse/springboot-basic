package com.mountain.voucherApp.shared.io;

import com.mountain.voucherApp.application.port.in.CustomerDto;
import com.mountain.voucherApp.adapter.out.persistence.voucher.VoucherEntity;

import java.util.List;

public interface Output {
    void printMessage(String msg);
    void printManual();
    void choiceDiscountPolicy();
    void printVoucherList(List<VoucherEntity> voucherEntityList);
    void printCustomerList(List<CustomerDto> customerDtoEntityList);
    void printCustomerVoucherInfo(List<CustomerDto> customerDtoEntityList);
    void printException(Exception e);
    void close();
}
