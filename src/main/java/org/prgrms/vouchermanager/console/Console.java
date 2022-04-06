package org.prgrms.vouchermanager.console;

import java.io.*;

public class Console implements Input, Output {

    private final BufferedReader br;
    private final BufferedWriter bw;
    private boolean initialized = false;

    public Console(InputStream is, PrintStream ps) {
        this.br = new BufferedReader(new InputStreamReader(is));
        this.bw = new BufferedWriter(new OutputStreamWriter(ps));
        init();
    }

    private void init() {
        if (initialized) return;
        String message = "Initialize message..";
        print(message);
        initialized = true;
    }

    @Override
    public String read() {
        try {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void print(String message) {
        try {
            bw.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
