package com.pppp0722.vouchermanagement.io;

import com.pppp0722.vouchermanagement.engine.command.CommandType;
import com.pppp0722.vouchermanagement.engine.command.EntityType;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import java.util.UUID;

public interface Input {

    String getCommand();

    CommandType inputCommandType();

    EntityType inputEntityType();

    VoucherType inputVoucherType();

    UUID inputMemberId();

    UUID inputVoucherId();

    String inputMemberReadType();

    String inputVoucherReadType();

    String inputMemberName();

    long inputVoucherAmount();
}
