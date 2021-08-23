package org.prgrms.kdtspringhw.utils;

import org.springframework.core.io.Resource;

import java.io.IOException;

public interface CsvReader<T> {
    T readCsv(Resource resource) throws IOException;
}
