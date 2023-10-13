package study.dev.spring.voucher.domain.discount;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[FixedDiscounter Test] - Domain Layer")
class FixedDiscounterTest {

	@Test
	@DisplayName("정액 할인을 수행한다")
	void discountTest() {
		//given
		FixedDiscounter fixedDiscounter = new FixedDiscounter();

		//when
		double actual = fixedDiscounter.discount(1000, 100);

		//then
		assertThat(actual).isEqualTo(900);
	}
}
