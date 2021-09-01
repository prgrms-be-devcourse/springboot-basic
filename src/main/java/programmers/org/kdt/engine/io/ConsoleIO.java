package programmers.org.kdt.engine.io;

public class ConsoleIO implements Input, Output {

    @Override
    public String input(String s) {
        return null;
    }

    @Override
    public void print(String message) {

    }

    public void help() {
        print("");
        print("=== Voucher Program ===");
        print("Type **exit** to exit the program.");
        print("Type **create** to create a new voucher.");
        print("Type **list** to list all vouchers.");
    }

    public void error() {
        print("Please try again.");
    }

    public void close() {}
}
