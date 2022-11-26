package prgms.vouchermanagementapp.repository.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileManager {

    public File getFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            createNewFile(file);
        }
        return file;
    }

    public void writeContent(File file, String content) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.append(content).append(System.lineSeparator());
        } catch (IOException ioException) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Cannot writing content ''{0}'' to file ''{1}''", content, file),
                    ioException
            );
        }
    }

    public List<String> readFileByLine(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return bufferedReader.lines()
                    .collect(Collectors.toList());
        } catch (IOException ioException) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Cannot read from file: ''{0}''", file),
                    ioException
            );
        }
    }

    private void createNewFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException ioException) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Cannot create file ''{0}''", file),
                    ioException
            );
        }
    }
}
