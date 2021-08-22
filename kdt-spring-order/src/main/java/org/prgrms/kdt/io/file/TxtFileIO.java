package org.prgrms.kdt.io.file;

import javax.annotation.PreDestroy;
import java.io.*;

public class TxtFileIO implements IO {

    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private File file;
    public TxtFileIO(String path) throws IOException {
        file = new File(path);

        if (!file.exists())
            file.createNewFile();

        this.bufferedReader = new BufferedReader(new FileReader(file));
        this.bufferedWriter = new BufferedWriter(new FileWriter(file, true));
    }

    @Override
    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    @Override
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

    @PreDestroy
    public void close() {
        try {
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
