package prgms.kdt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import prgms.kdt.io.Menu;
import prgms.kdt.io.Writer;

@SpringBootApplication
@Component
public class KdtApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KdtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Writer writer = new Writer();
		writer.printLine(Menu.GUIDE.getMenuLiterals());
	}
}
