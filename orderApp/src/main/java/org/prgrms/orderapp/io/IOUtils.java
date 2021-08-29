package org.prgrms.orderapp.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class IOUtils {

    public static List<String> loadCSV(String path) {
        List<String> lines = new ArrayList<>();
        String line = "";
        try (var br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static Optional<Object> loadByteFile(String path) {
        File file;
        if (path != null && (file = new File(path)).exists()) {
            try (var fis = new FileInputStream(file);
                 var ois = new ObjectInputStream(fis)) {
                return Optional.ofNullable(ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    public static void saveObject(Object obj, String path) {
        try (var fos = new FileOutputStream(path);
             var oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
