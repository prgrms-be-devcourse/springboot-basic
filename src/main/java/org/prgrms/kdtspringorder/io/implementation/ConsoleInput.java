package org.prgrms.kdtspringorder.io.implementation;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.prgrms.kdtspringorder.io.abstraction.Input;
import org.prgrms.kdtspringorder.io.domain.Command;

public class ConsoleInput implements Input {
  @Override
  public Command read() {
    Scanner scanner = new Scanner(System.in);
    String commands = scanner.nextLine();
    String[] tokens = commands.split(" ");

    // 한줄의 맨 앞 단어는 명령어
    String command = tokens[0];

    // 그 다음부터는 모두 옵션을 입력 받는다고 가정함
    List<String> options = Arrays
        .stream(tokens)
        .skip(1)
        .collect(Collectors.toList());
    return new Command(tokens[0], options);
  }
}
