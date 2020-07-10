package com.qingsw.naming;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class NamingApplicationTests {

	@Autowired
	GushiDataSource gushiDataSource;

	@Autowired
	private NamingService namingService;

	@Test
	void contextLoads() throws IOException {
		Shijing shijing = new Shijing("content", "title", "author", "dy");
		gushiDataSource.insert(shijing);

		List<Shijing> shijings = gushiDataSource.getAll();
		Gson gson = new GsonBuilder().create();
		for (Shijing item : shijings) {
			System.out.println(namingService.fromShiJing(item, "王"));
		}
	}

}
