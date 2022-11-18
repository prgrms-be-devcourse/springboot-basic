package org.prgrms.vouchermanagement.io;

public interface Input {
    String receiveCommand();

    int receiveDiscountAmount(String voucherTypeNumberInput);
    
    String receiveVoucherType();
}
