package prgms.spring_week1.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console implements Input,Output{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    @Override
    public String inputTextOption() throws IOException {
        return br.readLine();
    }

    @Override
    public void printInputMessage() {
        System.out.println("please input");
    }

    @Override
    public void printMenuList() {
        System.out.println("select menu");
    }
}
