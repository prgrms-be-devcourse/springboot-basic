package team.marco.vouchermanagementsystem.model;

import java.util.UUID;

public interface Voucher {
    UUID getId();

    String getInfo();

    VoucherType getType();
}
