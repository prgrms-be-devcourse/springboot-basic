package com.programmers.vouchermanagement.global.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TimeUtilsManager {

    public static LocalDateTime timestampToLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
