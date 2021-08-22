package org.prgrms.kdt.io.file;

import java.io.*;

public class CsvFileIO implements IO {

    private BufferedReader bufferedReader;
    private File file;

    public CsvFileIO(String path) throws IOException {
        this.file = new File(path);

        if (!file.exists())
            file.createNewFile();

        bufferedReader = new BufferedReader(new FileReader(file));
    }

    @Override
    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public void writeLine(String s) throws IOException {
        throw new IOException("Cannot write csv file");
    }

    @Override
    public void reset() {
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
