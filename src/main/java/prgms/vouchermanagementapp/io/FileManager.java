package prgms.vouchermanagementapp.io;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class FileManager {

    private final ApplicationContext applicationContext;

    public FileManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public File getFileByPath(String filePath) {
        Resource resource = applicationContext.getResource(filePath);
        return getFileByResource(resource);
    }

    private File getFileByResource(Resource resource) {
        try {
            return resource.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
