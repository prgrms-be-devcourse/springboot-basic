package com.example.voucherproject.voucher.model;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Voucher {
    private final UUID id;
    private VoucherType type;
    private Long amount;

    private final LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    public Long discount(Long originPrice){
        return type.discount(originPrice, amount);
    }
}
