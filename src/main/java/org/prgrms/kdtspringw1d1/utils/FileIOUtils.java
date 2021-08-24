package org.prgrms.kdtspringw1d1.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileIOUtils<T> {

    public List<T> readFile(String fileName){
        ClassPathResource resource = new ClassPathResource("filedb/" + fileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(resource.getFile());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                List<T> items = (List<T>) objectInputStream.readObject();
                objectInputStream.close();
                fileInputStream.close();
                return items;
            } catch (EOFException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    public T writeFile(String fileName, T item) {

        List<T> items = readFile(fileName);

        ClassPathResource resource = new ClassPathResource("filedb/" + fileName);
        try {
            FileOutputStream fileOutputStream =  new FileOutputStream(resource.getFile());;
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            items.add(item);
            objectOutputStream.writeObject(items);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

}
