package study.dev.spring.user.infrastructure.dto;

import java.util.UUID;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.dev.spring.user.domain.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserData {

	@CsvBindByName
	private UUID uuid;
	@CsvBindByName
	private String name;

	public User toUser() {
		return new User(uuid, name);
	}
}
