package programmers.org.kdt.engine.io;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

public class ConsoleTextIO extends ConsoleIO {
    private final TextIO textIO;

    public ConsoleTextIO() {
        textIO = TextIoFactory.getTextIO();
        print("This is Interactive Console Application text-io");
    }

    //input
    @Override
    public String input(String command) {
        return textIO.newStringInputReader().read(command);
    }

    //output
    @Override
    public void print(String message) {
        textIO.getTextTerminal().println(message);
    }

    @Override
    public void close() {
        textIO.dispose();
    }
}
