package org.prgrms.kdt.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DateTimeUtils {

    public static Timestamp timestampOf(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    public static LocalDateTime localDateTimeOf(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
