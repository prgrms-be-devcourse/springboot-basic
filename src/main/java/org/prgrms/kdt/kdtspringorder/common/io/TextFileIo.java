package org.prgrms.kdt.kdtspringorder.common.io;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TextFileIo implements FileIo{

    private String FILE_PATH = "voucher_list.txt";

    @Override
    public void write(List<String> strList) throws IOException {

        // 1. 파일 객체 생성
        File file = new File(FILE_PATH);

        // 2. 파일 존재여부 체크 및 생성
        if (!file.exists()) {
            file.createNewFile();
        }

        // 3. Writer 생성
        FileWriter fw = new FileWriter(file);
        PrintWriter writer = new PrintWriter(fw);

        // 4. 파일에 쓰기
        strList.forEach(s ->  writer.println(s));

        // 5. PrintWriter close
        writer.close();

    }

    @Override
    public List<String> readAllLines() throws IOException {
        Stream<String> stream = Files.lines(Paths.get(FILE_PATH), Charset.forName("utf-8"));
        List<String> strList = stream.collect(Collectors.toList());
        stream.close();
        return strList;
    }

}
