package com.mountain.voucherapp.common.io;

import com.mountain.voucherapp.dto.CustomerDto;
import com.mountain.voucherapp.model.VoucherEntity;

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
