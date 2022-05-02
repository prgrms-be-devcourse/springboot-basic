package com.prgrms.kdt.springbootbasic.inputPackage;

import com.prgrms.kdt.springbootbasic.returnFormats.VoucherInfo;

import java.io.IOException;

public interface CustomInput {

    String getCommand() throws IOException;

    VoucherInfo getNewVoucherInfo() throws IOException;
}
