package prgms.vouchermanagementapp.view;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class FileManager {

    private final ApplicationContext applicationContext;

    public FileManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void writeContents(File file, String contents) {
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.append(contents).append(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createNewFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
