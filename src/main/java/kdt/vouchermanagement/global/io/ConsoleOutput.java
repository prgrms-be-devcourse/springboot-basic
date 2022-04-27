package kdt.vouchermanagement.global.io;

import kdt.vouchermanagement.global.response.Response;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class ConsoleOutput implements Output {

    @Override
    public void printMenu(String message) {
        System.out.println(message);
    }

    @Override
    public void printResponse(Response response) {
        System.out.println(MessageFormat.format("{0} {1}", response.getStatusCode(), response.getData()));
    }
}
