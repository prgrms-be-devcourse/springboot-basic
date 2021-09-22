package W3D2.jcu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import lombok.SneakyThrows;

public class Utils {

    @SneakyThrows
    public static String readLine() {
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

    @SneakyThrows
    public static String readLineWithMessage(String message) {
        System.out.println(message);
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

}
