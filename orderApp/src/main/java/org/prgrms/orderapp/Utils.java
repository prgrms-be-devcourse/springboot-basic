package org.prgrms.orderapp;

import org.prgrms.orderapp.model.Voucher;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Utils {
    private static final String splitBy = ",";

    public static long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static List<String> loadCSV(String filename) {
        List<String> lines = new ArrayList<>();
        String line = "";
        try {
            var bufferedReader = new BufferedReader(new FileReader(filename));
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static Optional<Object> loadByteFile(String filename) {
        try {
            var file = new File(filename);
            if (!file.createNewFile()) {
                var fis = new FileInputStream(file);
                var ois = new ObjectInputStream(fis);

                var res = ois.readObject();

                ois.close();
                fis.close();
                return Optional.of(res);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static void saveObject(Object obj, String path) {
        try {
            var fos = new FileOutputStream(path);
            var oos = new ObjectOutputStream(fos);

            oos.writeObject(obj);

            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
