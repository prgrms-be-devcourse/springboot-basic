package com.voucher.vouchermanagement.utils.deserializer;

public interface CsvMapper<T> {
    T deserialize(String csvLine);

    String serialize(T target);
}
