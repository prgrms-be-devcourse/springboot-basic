package org.prgrms.kdt.io;

import org.springframework.stereotype.Component;

import java.io.*;

public class FileIo {

    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;
    private static File file;
    private FileIo() {
    }

    public static FileIo createFileIo(String path) throws IOException {
        FileIo fileIo = new FileIo();
        file = new File(path);

        if (!file.exists())
            file.createNewFile();

        bufferedReader = new BufferedReader(new FileReader(file));
        bufferedWriter = new BufferedWriter(new FileWriter(file, true));

        fileIo.setFileReader(bufferedReader);
        fileIo.setFileWriter(bufferedWriter);

        return fileIo;
    }

    private void setFileReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    private void setFileWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    public void writeLine(String s) throws IOException {
        bufferedWriter.write(s);
        bufferedWriter.flush();
    }


    public void reset() {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
