package org.prgrms.kdt.assignments;

import org.springframework.core.io.Resource;

import java.io.*;
import java.util.Map;
import java.util.stream.Collectors;

public class FileUtil {

    private static final String NewLine = "\r\n";

    private FileUtil() {}

    public static void write(String body, String resource) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(resource,true));
                bw.write(body + NewLine);
                bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String readText(String resource) {
        try {
            BufferedReader br = new BufferedReader(new BufferedReader(new FileReader(resource)));
            var brResult = br.readLine().toString();
            br.close();
            return brResult;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> readCSV(String resource) throws IOException {
        BufferedReader reader = new BufferedReader(new BufferedReader(new FileReader(resource)));
            return reader.lines()
                    .skip(1L)
                    .map(line -> line.split(","))
                    .collect(Collectors.toMap(line -> line[1], line -> line[0]));
    }
}
