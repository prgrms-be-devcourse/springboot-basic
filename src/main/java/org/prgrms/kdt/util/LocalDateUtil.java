package org.prgrms.kdt.util;

import java.time.LocalDate;

/**
 * Created by yhh1056
 * Date: 2021/09/11 Time: 4:57 오후
 */
public class LocalDateUtil {

    private LocalDateUtil() {}

    public static LocalDate parse(String datetime) {
        return LocalDate.parse(datetime);
    }

    public static boolean isValid(String datetime) {
        return datetime.matches("^((19|20)\\d\\d)?([- /.])?(0[1-9]|1[012])([- /.])?(0[1-9]|[12][0-9]|3[01])$");
    }

}
