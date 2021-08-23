package org.prgrms.kdt.util;

import java.util.Map;
import java.util.UUID;

public interface FileManager<T> {
    T fileToMemory(String filePath);

}
