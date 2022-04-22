package com.pppp0722.vouchermanagement.io;

import com.pppp0722.vouchermanagement.engine.command.EntityType;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import java.util.UUID;

public interface Input {

    String getCommand(String question);

    EntityType inputEntityType(String question);

    String inputCount();

    UUID inputMemberId();

    String inputMemberName();

    UUID inputVoucherId();

    VoucherType inputVoucherType();

    long inputVoucherAmount();
}
