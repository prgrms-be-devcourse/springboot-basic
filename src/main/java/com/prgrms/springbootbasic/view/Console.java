package com.prgrms.springbootbasic.view;

import com.prgrms.springbootbasic.domain.Voucher;
import java.util.Map;
import java.util.UUID;

public class Console implements Input, Output {

    @Override
    public String readCommand(String message) {
        return null;
    }

    @Override
    public String readVoucherType(String message) {
        return null;
    }

    @Override
    public String readVoucherValue(String message) {
        return null;
    }

    @Override
    public void printlnCommand(String message) {

    }

    @Override
    public void printCreateVoucherType() {

    }

    @Override
    public void printlnVoucherList(Map<UUID, Voucher> voucherMap) {

    }

    @Override
    public void errorMessage(String errorMessage) {

    }
}
