package org.prgrms.springorder.domain.voucher.api;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.prgrms.springorder.domain.customer.model.CustomerStatus;
import org.prgrms.springorder.domain.voucher.model.VoucherType;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@ToString
public class CustomerWithVoucher {

    private final UUID voucherId;

    private final long amount;

    private final LocalDateTime voucherCreatedAt;

    private final VoucherType voucherType;

    private final UUID customerId;

    private final String name;

    private final String email;

    private final CustomerStatus customerStatus;

}
