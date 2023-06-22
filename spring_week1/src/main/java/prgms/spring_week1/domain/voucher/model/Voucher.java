package prgms.spring_week1.domain.voucher.model;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface Voucher {
    UUID getVoucherId();
}
