package com.voucher.vouchermanagement.repository.utils;

public interface CsvDeserializer<T> {

  T deserialize(String csvLine);
}
