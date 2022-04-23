package com.pppp0722.vouchermanagement.io;

import com.pppp0722.vouchermanagement.engine.command.EntityType;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import java.util.UUID;

public interface Input {

    String getCommand();

    EntityType inputEntityType(String question);

    String inputCount();

    String inputMemberId();

    String inputMemberName();

    String inputVoucherId();

    VoucherType inputVoucherType();

    long inputVoucherAmount();
}
