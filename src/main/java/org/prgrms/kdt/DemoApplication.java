package org.prgrms.kdt;

import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.prgrms.kdt.model.Function;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		var applicationContext = SpringApplication.run(DemoApplication.class, args);
		var output= applicationContext.getBean(Output.class);
		var input = applicationContext.getBean(Input.class);

		output.printFunctions();

		while(true) {
			Optional<String> inputFunction = input.inputFunction();
			if(inputFunction.isEmpty()){
				output.printInputFunctionError();
				continue;
			}
		}

	}

}
