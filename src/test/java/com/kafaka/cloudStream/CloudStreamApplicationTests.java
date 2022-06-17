package com.kafaka.cloudStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafaka.cloudStream.vo.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

class CloudStreamApplicationTests {

	private ObjectMapper om = new ObjectMapper();

	@Test
	void contextLoads() throws JsonProcessingException {
		String str = "{\"foo\":\"failStr\"}";
		Foo foo = om.convertValue(om.readTree(str), Foo.class);
		System.out.println(foo);
	}

}
