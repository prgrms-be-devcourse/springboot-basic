package devcourse.springbootbasic.domain.customer;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public class Customer {

    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private final String name;
    private boolean isBlacklisted;

    public static Customer createCustomer(UUID uuid, String name, boolean isBlacklisted) {
        return Customer.builder()
                .id(uuid)
                .name(name)
                .isBlacklisted(isBlacklisted)
                .build();
    }

    public Customer updateBlacklistStatus(boolean isBlacklisted) {
        this.isBlacklisted = isBlacklisted;
        return this;
    }
}
