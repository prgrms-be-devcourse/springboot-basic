package org.prgrms.kdt.application.voucher.io;

import org.prgrms.kdt.application.voucher.type.CommandType;
import org.prgrms.kdt.application.voucher.type.VoucherType;

import java.util.Optional;

public interface Input {
    Optional<CommandType> inputCommand(); // 명령어 입력

    Optional<VoucherType> inputVoucherType(); // 바우처 종류 입력

    Optional<Long> inputVoucherTypeValue(String value); // 바우처 금액 or 퍼센트
}
