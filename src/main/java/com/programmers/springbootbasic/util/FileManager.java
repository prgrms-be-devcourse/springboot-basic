package com.programmers.springbootbasic.util;

import java.util.List;

public interface FileManager {

    boolean supports(String fileName);

    <T> List<T> read(String fileName, Class<T> type);

    <T> void write(T entity, String fileName, boolean append);
}
