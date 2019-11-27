package myspringBoot.mysb;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MysbApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("test 估計可能只有簡單的unit test，也許沒辦法autowire其它的東西");
	}

}
