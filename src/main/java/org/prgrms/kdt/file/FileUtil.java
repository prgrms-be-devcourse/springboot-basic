package org.prgrms.kdt.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Created by yhh1056
 * Date: 2021/08/19 Time: 11:24 오전
 */
public class FileUtil {

    private static final String NEW_LINE = "\r\n";

    private FileUtil() {}

    public static void write(String contents, String filename) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filename), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.append(contents).append(NEW_LINE);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
