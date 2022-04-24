package org.programs.kdt.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static void fileWriteLine(String filePath, String line) {
        File file = new File(filePath);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            bw.write(line);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<String> readFileByList(String filePath) {
        List<String> csvList = new ArrayList<>();
        try {
            File file = new File(filePath);
            csvList = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvList;
    }
}
