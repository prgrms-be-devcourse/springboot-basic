package com.example.voucherproject.common;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Utils {

    public static LocalDateTime convertLocalDateTimeFrom(String time){
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try{
            return LocalDateTime.parse(time + "T00:00:00.000", formatter);
        }
        catch (Exception e){
            return LocalDateTime.MIN.truncatedTo(ChronoUnit.MILLIS);
        }
    }
    public static LocalDateTime convertLocalDateTimeTo(String time){
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        try{
            return LocalDateTime.parse(time + "T00:00:00.000", formatter);
        }
        catch (Exception e){
            return LocalDateTime.MAX.truncatedTo(ChronoUnit.MILLIS);
        }
    }
    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
