package org.voucherProject.voucherProject.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class User {

    private final UUID userId;

    private final String userName;

    private final String userLoginId;
}
