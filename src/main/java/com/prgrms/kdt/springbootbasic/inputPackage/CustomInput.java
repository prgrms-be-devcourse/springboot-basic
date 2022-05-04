package com.prgrms.kdt.springbootbasic.inputPackage;

import com.prgrms.kdt.springbootbasic.returnFormats.VoucherInfo;

import java.io.IOException;
import java.util.Optional;

public interface CustomInput {

    Optional<String> getCommand() throws IOException;

    Optional<VoucherInfo> getNewVoucherInfo() throws IOException;
}
