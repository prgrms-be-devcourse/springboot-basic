package prgms.vouchermanagementapp.view;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;

@Component
public class FileManager {

    private final ApplicationContext applicationContext;

    public FileManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void writeContents(File file, String contents) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.append(contents).append(System.lineSeparator());
        } catch (IOException ioException) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Cannot writing contents ''{0}'' to file ''{1}''", contents, file),
                    ioException
            );
        }
    }

    public File initializeFileWithContents(String filepath, String contents) {
        File file = new File(filepath);

        if (!file.exists()) {
            createNewFile(file);
            writeContents(file, contents);
        }

        return file;
    }

    public File getFileByPath(String filePath) {
        return getFileByResource(applicationContext.getResource(filePath));
    }

    private File getFileByResource(Resource resource) {
        try {
            return resource.getFile();
        } catch (IOException ioException) {
            throw new IllegalArgumentException(
                    MessageFormat.format("Cannot find a file from resource ''{0}''", resource),
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
