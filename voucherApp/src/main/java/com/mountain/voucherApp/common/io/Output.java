package com.mountain.voucherApp.common.io;

import com.mountain.voucherApp.dto.CustomerDto;
import com.mountain.voucherApp.model.VoucherEntity;

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
