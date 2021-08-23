package org.prgrms.kdt.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class FileUtils {

    public static boolean isExistFile(Path fileFullPath) {
        return Files.exists(fileFullPath);
    }

    public static List<String> getReadAllLines(Path fileFullPath) {
        try {
            return Files.readAllLines(fileFullPath);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
