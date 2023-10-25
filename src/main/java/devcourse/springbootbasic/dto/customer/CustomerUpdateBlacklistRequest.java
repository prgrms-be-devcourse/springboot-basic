package devcourse.springbootbasic.dto.customer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class CustomerUpdateBlacklistRequest {

    private final UUID id;
    private final boolean isBlacklisted;
}
