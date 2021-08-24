package org.prgrms.kdt.kdtspringorder.common.io;

import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("file-object-io")
public class FileObjectIo implements FileIo<Object>{

    @Value(value = "${kdt.dev.file-io.file-path}")
    private String FILE_PATH;

    @PostConstruct
    public void postConstruct() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    @Override
    public void write(List<Object> oList){

        FileOutputStream fileStream = null; // 파일에 쓰는 역할
        ObjectOutputStream os = null; // 파일에 쓰기전에 직렬화 하는 역할
        try {

            fileStream = new FileOutputStream(FILE_PATH);
            os = new ObjectOutputStream(fileStream);

            for (int i = 0; i < oList.size(); i++) {
                os.writeObject(oList.get(i)); // 위 두개 스트림을 타고 직렬화->파일출력 수행
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

            fileStream = new FileInputStream(FILE_PATH);
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
