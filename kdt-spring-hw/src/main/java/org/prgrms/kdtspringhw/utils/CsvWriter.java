package org.prgrms.kdtspringhw.utils;

import org.prgrms.kdtspringhw.blacklist.BlackList;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;



public interface CsvWriter {
    void writeCsv(Resource resource, Map<UUID, BlackList> map) throws IOException;//제네릭 어떻게 쓰나용
}
