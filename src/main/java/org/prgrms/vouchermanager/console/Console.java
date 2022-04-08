package org.prgrms.vouchermanager.console;

import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 터미널 역할을 하는 콘솔 클래스
 */
@Component
public class Console implements Input, Output {

    private final BufferedReader br;
    private final BufferedWriter bw;

    public Console(InputStream is, PrintStream ps) {
        this.br = new BufferedReader(new InputStreamReader(is));
        this.bw = new BufferedWriter(new OutputStreamWriter(ps));
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
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
