package com.prgrms.common.util;

import java.time.LocalDateTime;

public interface Generator {
    String makeKey();

    LocalDateTime makeDate();

}
