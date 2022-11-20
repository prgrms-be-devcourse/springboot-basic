package prgms.vouchermanagementapp.view;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Reader {

    private final BufferedReader bufferedReader;

    public Reader() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readLine() {
        try {
            return bufferedReader.readLine();
        } catch (IOException ioException) {
            throw new IllegalArgumentException(ioException);
        }
    }
}
