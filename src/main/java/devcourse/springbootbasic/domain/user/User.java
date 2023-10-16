package devcourse.springbootbasic.domain.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
public class User {

    @Builder.Default
    private final UUID id = UUID.randomUUID();
    private final String name;
    private final boolean isBlacklisted;

    public static User createUser(UUID uuid, String name, boolean isBlacklisted) {
        return User.builder()
                .id(uuid)
                .name(name)
                .isBlacklisted(isBlacklisted)
                .build();
    }
}
