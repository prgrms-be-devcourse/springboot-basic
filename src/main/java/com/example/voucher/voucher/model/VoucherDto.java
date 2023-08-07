package com.example.voucher.voucher.model;

import java.util.UUID;
import com.example.voucher.constant.VoucherType;
import com.example.voucher.query.marker.Entity;

public class VoucherDto implements Entity {

    private UUID voucher_id;
    private VoucherType voucher_type;
    private long discount_value;

}
