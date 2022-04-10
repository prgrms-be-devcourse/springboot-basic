package org.prgrms.vouchermanager.console;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 터미널 역할을 하는 콘솔 클래스
 */
@Component
public class Console implements Input, Output {

    private final BufferedReader br;
    private final BufferedWriter bw;

    public Console(InputStream is, PrintStream ps) {
        this.br = new BufferedReader(new InputStreamReader(is, UTF_8));
        this.bw = new BufferedWriter(new OutputStreamWriter(ps, UTF_8));
    }

    @PreDestroy
     private void closeStream(){
        close();
    }

    private void close() {
        try {
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
