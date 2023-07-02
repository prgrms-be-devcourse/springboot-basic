package org.weekly.weekly.ui.reader;

import org.springframework.stereotype.Component;
import org.weekly.weekly.ui.exception.InputException;
import org.weekly.weekly.util.ExceptionMsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class BufferedReaderWrap implements CommandReader {
    private final BufferedReader bufferedReader;

    public BufferedReaderWrap() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String readLine(){
        try {
            return this.bufferedReader.readLine();
        } catch (IOException exception) {
            throw new InputException(ExceptionMsg.NOT_INPUT_FORMAT);
        }
    }
}
