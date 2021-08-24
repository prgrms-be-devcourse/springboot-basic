package org.prgrms.kdt.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.core.io.Resource;

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

    public static void read(String filename) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filename))) {
            reader.lines().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readCSV(Resource resource) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return reader.lines()
                    .skip(1L)
                    .map(line -> line.split(","))
                    .flatMap(Arrays::stream)
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
