package org.prgrms.kdt.global;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Generator {
    UUID generateId();
    LocalDateTime generateTime();
}
