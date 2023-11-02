package devcourse.springbootbasic.domain.customer;

import devcourse.springbootbasic.util.UUIDUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class Customer {

    @Builder.Default
    private final UUID id = UUIDUtil.generateRandomUUID();
    private final String name;
    private boolean isBlacklisted;

    public static Customer createCustomer(UUID uuid, String name, boolean isBlacklisted) {
        return Customer.builder()
                .id(uuid)
                .name(name)
                .isBlacklisted(isBlacklisted)
                .build();
    }

    public Customer applyBlacklist() {
        this.isBlacklisted = true;
        return this;
    }

    public Customer releaseBlacklist() {
        this.isBlacklisted = false;
        return this;
    }
}
