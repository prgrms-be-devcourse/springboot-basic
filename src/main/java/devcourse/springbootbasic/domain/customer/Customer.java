package devcourse.springbootbasic.domain.customer;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
public class Customer {

    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private final String name;
    private final boolean isBlacklisted;

    public static Customer createCustomer(UUID uuid, String name, boolean isBlacklisted) {
        return Customer.builder()
                .id(uuid)
                .name(name)
                .isBlacklisted(isBlacklisted)
                .build();
    }
}
