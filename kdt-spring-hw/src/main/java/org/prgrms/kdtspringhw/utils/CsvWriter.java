package org.prgrms.kdtspringhw.utils;

import org.prgrms.kdtspringhw.blacklist.BlackList;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;



public interface CsvWriter <T> {
    void writeCsv(Map<UUID, T> map) throws IOException;
}
