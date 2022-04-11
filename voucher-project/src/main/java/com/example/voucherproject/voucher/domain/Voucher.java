package com.example.voucherproject.voucher.domain;
import com.example.voucherproject.common.enums.VoucherType;
import lombok.*;
import org.springframework.stereotype.Component;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@ToString
public abstract class Voucher {
    private final UUID id;
    private final VoucherType type;
}
