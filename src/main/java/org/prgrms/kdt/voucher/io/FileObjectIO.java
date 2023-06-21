package org.prgrms.kdt.voucher.io;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("file-object-io")
public class FileObjectIO implements FileIO<Object>{


    @Value(value = "${file.voucher.file-path}")
    private String FILE_PATH;

    File file = new File("src/voucher-list.txt");
    @PostConstruct
    public void postConstruct() throws IOException {
//        File file = new File(FILE_PATH);

        // 파일 존재 확인
        if (file.exists()) {
            System.out.println("파일이 있습니다.");
        } else {
            System.out.println("파일이 없습니다.");
        }
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    @Override
    public void write(List<Object> oList){

        FileOutputStream fileStream = null; // 파일에 쓰는 역할
        ObjectOutputStream os = null; // 파일에 쓰기전에 직렬화 하는 역할
        try {

            fileStream = new FileOutputStream("src/voucher-list.txt");
            os = new ObjectOutputStream(fileStream);

            for (Object o : oList) {
                os.writeObject(o); // 위 두개 스트림을 타고 직렬화->파일출력 수행
            }

            os.close();
            fileStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Object> readAllLines(){

        FileInputStream fileStream = null; // 직렬화해서 썼던 파일을 다시 읽어오는 역할
        ObjectInputStream is = null; // 읽어온 직렬화된 내용을 역직렬화 하는 역할
        List<Object> oList = new ArrayList<>();

        try {

            fileStream = new FileInputStream(file);
            is = new ObjectInputStream(fileStream);

            Object o;

            while ( (o = is.readObject()) != null ) {
                oList.add(o);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return oList;
        }

    }

}
