package org.prgrms.vouchermanager.domain.blacklist.domain;

import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
public class Blacklist {
    private final UUID id = UUID.randomUUID();
    private final String email;

    public Blacklist(String email) {
        checkArgument(Strings.isNotBlank(email), "email은 공백이 될 수 없습니다.");
        checkArgument(email.contains("@"), "email은 @를 포함해야 합니다.");

        this.email = email;
    }
}
