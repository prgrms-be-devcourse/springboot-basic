package org.prgrms.kdt.fileutil;

import org.prgrms.kdt.domain.Voucher;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileIOStream {


    public void fileInputStream(Voucher v) throws IOException {
        String fileName = "voucher_file.txt";
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));
        bw.write(v+"\r\n");
        bw.flush();
    }

    public List<String> fileOutputStream() throws IOException {
        List<String> list = new ArrayList<>();
        String fileName = "voucher_file.txt";
        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
        String v = null;
        while ((v = fileReader.readLine()) != null){
            list.add(v);
        }
        fileReader.close();
        return list;
    }



}
