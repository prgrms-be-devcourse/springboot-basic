package org.prgrms.java.domain.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class VoucherDto {
    private final String voucherId;
    private final String ownerId;
    private final long amount;
    private final String type;
    private final boolean used;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime expiredAt;
}
