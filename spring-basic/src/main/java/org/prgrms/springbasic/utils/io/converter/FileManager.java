package org.prgrms.springbasic.utils.io.converter;

import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.utils.exception.NotExistEnumType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.prgrms.springbasic.utils.enumm.ErrorMessage.NOT_EXIST_ENUM_TYPE;

@Slf4j
public class FileManager<T> {
    private BufferedWriter writer;
    private BufferedReader reader;
    private final File file;
    private final String path;
    private int count;

    public FileManager(String path) {
        this.path = path;
        this.file = new File(path);
    }

    public void toFile(T object) {
        try (var writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(object.toString());
            writer.write("\n");
        } catch (IOException e) {
            log.error("Got IOException: {}", e.getMessage());
        }
    }

    public List<T> objectToList(Class<T> clazz) {
        List<T> list = new ArrayList<>();

        String line;

        try (var reader = new BufferedReader(new FileReader(path))) {
            while((line = reader.readLine()) != null) {
                var object = (T) toObject(clazz, line);
                list.add(object);
            }
        } catch (IOException e) {
            log.error("Got IOException: {}", e.getMessage());
        }

        return list;
    }

    public int countLines() {
        try (var reader = new BufferedReader(new FileReader(path))) {
            count = (int) reader.lines().count();
        } catch (IOException e) {
            log.error("Got IOException: {}", e.getMessage());
        }

        return count;
    }

    public void clear() {
        file.deleteOnExit();
    }

    private T toObject(Class<T> clazz, String line) {
        ObjectType objectType = null;

        try {
            objectType = ObjectType.getObjectType(clazz)
                    .orElseThrow(() -> new NotExistEnumType(NOT_EXIST_ENUM_TYPE.getMessage()));
        } catch (NotExistEnumType e) {
            log.error("Got not exist enum type: {}", clazz.getName());
        }

        String[] items = line.split(","); //UUID, EnumType, argument

        assert objectType != null;

        return (T) objectType.toObject(items);
    }
}
