package study.dev.spring.user.infrastructure.dto;

import java.util.UUID;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

	@CsvBindByName
	private UUID uuid;
	@CsvBindByName
	private String name;
}
