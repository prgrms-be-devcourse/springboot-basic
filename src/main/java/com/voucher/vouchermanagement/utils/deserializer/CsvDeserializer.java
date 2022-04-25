package com.voucher.vouchermanagement.utils.deserializer;

public interface CsvDeserializer<T> {
    T deserialize(String csvLine);
}
