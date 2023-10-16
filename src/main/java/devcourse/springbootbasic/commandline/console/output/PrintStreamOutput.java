package devcourse.springbootbasic.commandline.console.output;

public class PrintStreamOutput implements ConsoleOutput {

    public void println(String message) {
        System.out.println(message);
    }

    public void print(String message) {
        System.out.print(message);
    }

    public void printEmptyLine() {
        System.out.println();
    }
}
