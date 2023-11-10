package team.marco.voucher_management_system.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;

public abstract class StdIOTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    final void init() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    protected void setStdin(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    protected void setStdin(String delimiter, String... inputs) {
        String joinedInput = String.join(delimiter, inputs);

        setStdin(joinedInput);
    }

    protected String getStdout() {
        return outputStreamCaptor.toString();
    }
}
