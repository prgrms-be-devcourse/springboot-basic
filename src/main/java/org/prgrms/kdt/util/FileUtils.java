package org.prgrms.kdt.util;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;

public class FileUtils {

    private static final String COMMA_SPLIT = ",";

    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static List<String[]> readCSV(Resource resource) {
        try (var br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return br.lines().skip(1L)
                .map(line -> line.split(COMMA_SPLIT))
                .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
